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
    private final TokenLoginService tokenLoginService; // 변수명 수정
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/reissue") // POST 요청을 '/reissue' 경로로 매핑합니다.
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        // HTTP 요청에서 'Authorization' 헤더를 통해 리프레시 토큰을 받아옵니다.
        String refresh = request.getHeader("Authorization");
        if (refresh == null || !refresh.startsWith("Bearer ")) { // 토큰이 없거나 Bearer 타입이 아니면 에러 반환
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }
        String token = refresh.substring("Bearer ".length()); // 실제 토큰 값을 추출합니다.
        // 토큰 만료 여부 검사
        try {
            if (jwtUtil.isTokenExpired(token)) {
                // 리프레시 토큰이 만료되면 데이터베이스에서 삭제합니다.
                tokenLoginService.deleteByRefreshToken(token);
                return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
            }
        } catch (ExpiredJwtException e) {
            // 리프레시 토큰이 만료되면 데이터베이스에서 삭제합니다.
            tokenLoginService.deleteByRefreshToken(token);
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰이 맞는지 카테고리로 확인합니다.
        String category = jwtUtil.getCategoryFromToken(token);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // 토큰에서 사용자 아이디 추출합니다.
        String username = jwtUtil.getUserEmailFromToken(token);

        // 사용자 정보 조회
        Optional<User> userOptional = userService.findByUserId(username);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }

        // 리프레시 토큰이 유효한지 확인합니다.
        Optional<TokenLogin> refreshTokenOptional = tokenLoginService.findByRefreshToken(token);
        if (refreshTokenOptional.isEmpty() || !refreshTokenOptional.get().getUser().equals(userOptional.get())) {
            return new ResponseEntity<>("refresh token not found or does not match", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰의 상태 검증
        TokenLogin refreshToken = refreshTokenOptional.get();
        if (!refreshToken.getStatus().equals("activated")) {
            return new ResponseEntity<>("refresh token invalid or expired", HttpStatus.BAD_REQUEST);
        }

        // 새로운 액세스 토큰 생성
        // 액세스 토큰의 유효 시간 (밀리초 단위)
        Long accessExpiredMs = 600000L;
        String access = jwtUtil.generateToken(username, "access", accessExpiredMs);

        // 응답에 새로운 액세스 토큰 추가
        response.addHeader("Authorization", "Bearer " + access);

        // 클라이언트가 Authorization 헤더를 읽을 수 있도록 설정
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        // 성공적으로 새 토큰을 발급받았을 때의 응답
        return new ResponseEntity<>(HttpStatus.OK);
    }
}