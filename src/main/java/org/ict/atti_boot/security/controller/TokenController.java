package org.ict.atti_boot.security.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.ict.atti_boot.security.service.TokenLoginService;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        log.info("reissue 요청: {}", request.getRequestURI());

        // Authorization 헤더에서 리프레시 토큰과 액세스 토큰을 추출
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

        // 리프레시 토큰의 상태 확인
        Optional<TokenLogin> refreshTokenOptional = tokenLoginService.findByRefreshToken(refreshToken);
        if (refreshTokenOptional.isEmpty()) {
            log.info("리프레시 토큰을 찾을 수 없습니다. DB에서 사용 가능한 토큰을 찾습니다: {}", refreshToken);

            // AccessToken에서 사용자 ID 추출
            String userIdFromAccessToken;
            try {
                userIdFromAccessToken = jwtUtil.getUserIdFromToken(accessToken);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token not valid");
            }

            // 사용자 ID로 기존 TokenLogin 정보 조회
            refreshTokenOptional = tokenLoginService.findByUserUserId(userIdFromAccessToken);
            if (refreshTokenOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found");
            }

            refreshToken = refreshTokenOptional.get().getRefreshToken(); // 기존 리프레시 토큰 사용
        }

        TokenLogin tokenLogin = refreshTokenOptional.get();
        String username;
        try {
            // 리프레시 토큰에서 사용자 이메일 추출
            username = jwtUtil.getUserEmailFromToken(refreshToken);
            log.info("토큰에서 사용자 이메일 추출: {}", username);
        } catch (ExpiredJwtException e) {
            log.info("리프레시 토큰이 만료됨");
            tokenLoginService.deleteByRefreshToken(refreshToken);
            // 새로운 리프레시 토큰 및 액세스 토큰 발급
            return generateNewTokensForUser(tokenLogin.getUser(), response);
        } catch (Exception e) {
            log.error("유효하지 않은 리프레시 토큰", e);
            tokenLoginService.deleteByRefreshToken(refreshToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        // 사용자 이메일로 사용자 정보 조회
        Optional<User> userOptional = userService.findByEmail(username);
        if (userOptional.isEmpty()) {
            log.info("User not found: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // 액세스 토큰의 상태 확인
        boolean accessTokenExpired = jwtUtil.isTokenExpired(accessToken);
        if (accessTokenExpired) {
            Optional<TokenLogin> existingTokenLoginOptional = tokenLoginService.findByUserUserId(userOptional.get().getUserId());
            if (existingTokenLoginOptional.isPresent()) {
                TokenLogin existingTokenLogin = existingTokenLoginOptional.get();
                accessToken = existingTokenLogin.getAccessToken();
            }
        }

        // 토큰 상태나 사용자 일치 여부 확인
        if (!tokenLogin.getUser().equals(userOptional.get()) || !"activated".equals(tokenLogin.getStatus())) {
            log.info("유효하지 않은 리프레시 토큰 상태 또는 사용자 불일치");
            tokenLoginService.deleteByRefreshToken(refreshToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 리프레시 토큰");
        }

        // RefreshToken이 만료되지 않았고 AccessToken이 만료된 경우
        boolean refreshTokenExpired = jwtUtil.isTokenExpired(refreshToken);

        if (!refreshTokenExpired && accessTokenExpired) {
            // 새로운 AccessToken 발급 (만료 시간 1분)
            Long accessExpiredMs = 60000L; // 1분
            String newAccessToken = jwtUtil.generateToken(username, "access", accessExpiredMs);
            tokenLogin.setAccessToken(newAccessToken);
            tokenLoginService.save(tokenLogin);
            response.addHeader("Authorization", "Bearer " + newAccessToken);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            log.info("새로운 accessToken 발급함: {}", newAccessToken);

            return ResponseEntity.ok().build();
        }

        if (refreshTokenExpired && !accessTokenExpired) {
            // 새로운 RefreshToken과 AccessToken 발급
            return generateNewTokensForUser(userOptional.get(), response);
        }

        if (refreshTokenExpired && accessTokenExpired) {
            // 두 토큰 모두 만료된 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Both tokens expired, please log in again.");
        }

        // 리프레시 토큰이 만료되지 않았고, 액세스 토큰도 만료되지 않은 경우
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> generateNewTokensForUser(User user, HttpServletResponse response) {
        String username = user.getEmail();

        // 새로운 AccessToken 발급 (만료 시간 1분)
        //Long accessExpiredMs = 60000L; // 1분
        Long accessExpiredMs = 10000L; // 10초 test
        String newAccessToken = jwtUtil.generateToken(username, "access", accessExpiredMs);
        response.addHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        // 새로운 리프레시 토큰 발급 (만료 시간 2시간)
        //Long refreshExpiredMs = 7200000L; // 2시간
        Long refreshExpiredMs = 60000L; //1분
        String newRefreshToken = jwtUtil.generateToken(username, "refresh", refreshExpiredMs);

        // 새롭게 발급된 토큰 정보를 DB에 저장
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