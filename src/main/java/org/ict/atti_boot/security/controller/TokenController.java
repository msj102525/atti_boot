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

//    @PostMapping("/reissue")
//    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//        log.info("reissue 요청: {}", request.getRequestURI());
//
//        String refresh = request.getHeader("Authorization");
//        if (refresh == null || !refresh.startsWith("Bearer ")) {
//            return ResponseEntity.badRequest().body("Refresh token is missing or invalid");
//        }
//
//        String refreshToken = refresh.substring("Bearer ".length());
//        if (refreshToken.isEmpty()) {
//            log.info("Refresh token is empty");
//            return ResponseEntity.badRequest().body("Refresh token is missing or invalid");
//        }
//
//        log.info("Received refresh token: {}", refreshToken);
//
//        String username;
//        try {
//            username = jwtUtil.getUserEmailFromToken(refreshToken);
//        } catch (ExpiredJwtException e) {
//            log.info("Refresh token expired");
//            username = e.getClaims().getSubject(); // 만료된 토큰에서도 사용자 이메일을 추출
//        } catch (Exception e) {
//            log.error("Invalid refresh token", e);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
//        }
//
//        Optional<User> userOptional = userService.findByEmail(username);
//        if (userOptional.isEmpty()) {
//            log.info("User not found: {}", username);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        Optional<TokenLogin> refreshTokenOptional = tokenLoginService.findByRefreshToken(refreshToken);
//        if (refreshTokenOptional.isEmpty()) {
//            log.info("Refresh token not found: {}", refreshToken);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found");
//        }
//
//        TokenLogin tokenLogin = refreshTokenOptional.get();
//        if (!tokenLogin.getUser().equals(userOptional.get()) || !"activated".equals(tokenLogin.getStatus())) {
//            log.info("Invalid refresh token status or user mismatch");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
//        }
//
//        Long accessExpiredMs = 1000L;
//        Long refreshExpiredMs = 6000L;
//
//        // 새로운 AccessToken 발급
//        String newAccessToken = jwtUtil.generateToken(username, "access", accessExpiredMs);
//        response.addHeader("Authorization", "Bearer " + newAccessToken);
//        response.setHeader("Access-Control-Expose-Headers", "Authorization");
//
//        // RefreshToken 만료 여부 확인 및 갱신
//        boolean refreshTokenExpired = jwtUtil.isTokenExpired(refreshToken);
//        if (refreshTokenExpired) {
//            String newRefreshToken = jwtUtil.generateToken(username, "refresh", refreshExpiredMs);
//            tokenLogin.setRefreshToken(newRefreshToken);
//            tokenLoginService.save(tokenLogin);
//            response.addHeader("Refresh-Token", "Bearer " + newRefreshToken);
//            response.setHeader("Access-Control-Expose-Headers", "Authorization, Refresh-Token");
//            log.info("New access and refresh tokens generated");
//        } else {
//            log.info("New access token generated");
//        }
//
//        return ResponseEntity.ok().build();
//    }
@PostMapping("/reissue")
public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
    log.info("reissue 요청: {}", request.getRequestURI());

    String refresh = request.getHeader("Authorization");
    if (refresh == null || !refresh.startsWith("Bearer ")) {
        return ResponseEntity.badRequest().body("Refresh token is missing or invalid");
    }

    String refreshToken = refresh.substring("Bearer ".length());
    log.info("Received refresh token: {}", refreshToken);

    String username;
    try {
        username = jwtUtil.getUserEmailFromToken(refreshToken);
    } catch (ExpiredJwtException e) {
        log.info("Refresh token expired");
        tokenLoginService.deleteByRefreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
    } catch (Exception e) {
        log.error("Invalid refresh token", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }

    Optional<User> userOptional = userService.findByEmail(username);
    if (userOptional.isEmpty()) {
        log.info("User not found: {}", username);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    Optional<TokenLogin> refreshTokenOptional = tokenLoginService.findByRefreshToken(refreshToken);
    if (refreshTokenOptional.isEmpty()) {
        log.info("Refresh token not found: {}", refreshToken);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found");
    }

    TokenLogin tokenLogin = refreshTokenOptional.get();
    if (!tokenLogin.getUser().equals(userOptional.get()) || !"activated".equals(tokenLogin.getStatus())) {
        log.info("Invalid refresh token status or user mismatch");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }

    Long accessExpiredMs = 600000L; // 1분
    Long refreshExpiredMs = 2700000L; // 2시간

    // 새로운 AccessToken 발급
    String newAccessToken = jwtUtil.generateToken(username, "access", accessExpiredMs);
    response.addHeader("Authorization", "Bearer " + newAccessToken);
    response.setHeader("Access-Control-Expose-Headers", "Authorization");

    // RefreshToken 만료 여부 확인 및 갱신
    boolean refreshTokenExpired = jwtUtil.isTokenExpired(refreshToken);
    if (refreshTokenExpired) {
        String newRefreshToken = jwtUtil.generateToken(username, "refresh", refreshExpiredMs);
        tokenLogin.setRefreshToken(newRefreshToken);
        tokenLoginService.save(tokenLogin);
        response.addHeader("Refresh-Token", "Bearer " + newRefreshToken);
        response.setHeader("Access-Control-Expose-Headers", "Authorization, Refresh-Token");
        log.info("New access and refresh tokens generated");
    } else {
        log.info("New access token generated");
    }

    return ResponseEntity.ok().build();
}

}
