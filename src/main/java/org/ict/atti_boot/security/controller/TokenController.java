package org.ict.atti_boot.security.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.handler.CustomLogoutHandler;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.ict.atti_boot.security.service.TokenLoginService;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
public class TokenController {
    private final TokenLoginService tokenLoginService;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final CustomLogoutHandler customLogoutHandler; // CustomLogoutHandler 주입

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        log.info("reissue 요청: {}", request.getRequestURI());

        String refresh = request.getHeader("Authorization");
        String token = request.getHeader("Token");

        if (refresh == null || !refresh.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("리프레시 토큰이 없거나 유효하지 않습니다");
        }

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("액세스 토큰이 없거나 유효하지 않습니다");
        }

        String refreshToken = refresh.substring("Bearer ".length());
        String accessToken = token.substring("Bearer ".length());
        log.info("리프레시 토큰을 받음: {}", refreshToken);
        log.info("액세스 토큰을 받음: {}", accessToken);

        // 액세스 토큰이 유효하지 않을 경우 DB에서 조회 및 검증
        if (!jwtUtil.isTokenValid(accessToken)) {
            log.info("액세스 토큰이 유효하지 않음, DB에서 토큰 조회: {}", accessToken);
            Optional<TokenLogin> dbTokenOptional = tokenLoginService.findByAccessToken(accessToken);
            if (dbTokenOptional.isEmpty() || jwtUtil.isTokenExpired(dbTokenOptional.get().getAccessToken())) {
                log.error("DB에 저장된 토큰이 유효하지 않음");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not valid");
            }
            accessToken = dbTokenOptional.get().getAccessToken();
        }

        // 리프레시 토큰의 상태 확인
        Optional<TokenLogin> refreshTokenOptional = tokenLoginService.findByRefreshToken(refreshToken);
        if (refreshTokenOptional.isEmpty()) {
            log.info("리프레시 토큰을 찾을 수 없습니다. DB에서 사용 가능한 토큰을 찾습니다: {}", refreshToken);

            String userIdFromAccessToken;
            try {
                userIdFromAccessToken = jwtUtil.getUserIdFromToken(accessToken);
            } catch (Exception e) {
                log.error("액세스 토큰이 유효하지 않습니다: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not valid");
            }

            refreshTokenOptional = tokenLoginService.findByUserUserId(userIdFromAccessToken);
            if (refreshTokenOptional.isEmpty()) {
                log.error("리프레시 토큰을 찾을 수 없습니다: userId {}", userIdFromAccessToken);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found");
            }
            refreshToken = refreshTokenOptional.get().getRefreshToken();
        }

        TokenLogin tokenLogin = refreshTokenOptional.get();
        String username;
        try {
            username = jwtUtil.getUserEmailFromToken(refreshToken);
            log.info("토큰에서 사용자 이메일 추출: {}", username);
        } catch (ExpiredJwtException e) {
            log.info("리프레시 토큰이 만료됨");
            handleLogoutIfBothTokensExpired(request, response, tokenLogin);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired. Please log in again.");
        } catch (Exception e) {
            log.error("유효하지 않은 리프레시 토큰", e);
            handleLogoutIfBothTokensExpired(request, response, tokenLogin);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        Optional<User> userOptional = userService.findByEmail(username);
        if (userOptional.isEmpty()) {
            log.error("사용자를 찾을 수 없습니다: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        boolean accessTokenExpired = jwtUtil.isTokenExpired(accessToken);
        boolean refreshTokenExpired = jwtUtil.isTokenExpired(refreshToken);

        if (accessTokenExpired && refreshTokenExpired) {
            log.info("두 토큰 모두 만료됨");
            handleLogoutIfBothTokensExpired(request, response, tokenLogin);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Both tokens expired, please log in again.");
        }

        if (!refreshTokenExpired && accessTokenExpired) {
            Long accessExpiredMs = 1500000L; // 25분
            String newAccessToken = jwtUtil.generateToken(username, "access", accessExpiredMs);
            tokenLogin.setAccessToken(newAccessToken);
            tokenLoginService.save(tokenLogin);
            response.addHeader("Authorization", "Bearer " + newAccessToken);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            log.info("새로운 accessToken 발급함: {}", newAccessToken);

            return ResponseEntity.ok().build();
        }

        if (refreshTokenExpired && !accessTokenExpired) {
            log.info("리프레시 토큰 만료, 새로운 토큰 발급");
            return generateNewTokensForUser(userOptional.get(), response);
        }

        log.info("리프레시토큰, 액세스 토큰이 만료되지 않음");
        return ResponseEntity.ok().build();
    }

    private void handleLogoutIfBothTokensExpired(HttpServletRequest request, HttpServletResponse response, TokenLogin tokenLogin) {
        tokenLoginService.deleteByRefreshToken(tokenLogin.getRefreshToken());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        customLogoutHandler.logout(request, response, auth); // 로그아웃 처리
    }

    private ResponseEntity<?> generateNewTokensForUser(User user, HttpServletResponse response) {
        String username = user.getEmail();

        Long accessExpiredMs = 1500000L; // 25분
        String newAccessToken = jwtUtil.generateToken(username, "access", accessExpiredMs);
        response.addHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        Long refreshExpiredMs = 3600000L; // 1시간
        String newRefreshToken = jwtUtil.generateToken(username, "refresh", refreshExpiredMs);

        TokenLogin newTokenLogin = new TokenLogin();
        newTokenLogin.setUser(user);
        newTokenLogin.setAccessToken(newAccessToken);
        newTokenLogin.setRefreshToken(newRefreshToken);
        newTokenLogin.setStatus("activated");
        tokenLoginService.save(newTokenLogin);

        response.addHeader("Refresh-Token", "Bearer " + newRefreshToken);
        response.setHeader("Access-Control-Expose-Headers", "Authorization, Refresh-Token");
        log.info("새로운 accessToken 및 refreshToken 발급함: {}", newAccessToken);

        return ResponseEntity.ok().build();
    }
}
