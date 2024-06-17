package org.ict.atti_boot.security.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.ict.atti_boot.security.service.TokenLoginService;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    private final JWTUtil jwtUtil;
    private final TokenLoginService tokenLoginService;
    private final UserService userService;

    public CustomLogoutHandler(JWTUtil jwtUtil, TokenLoginService tokenLoginService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.tokenLoginService = tokenLoginService;
        this.userService = userService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7); // 'Bearer ' 문자 제거

            try {
                // JWT 토큰 만료 여부 확인
                jwtUtil.isTokenExpired(token);
            } catch (ExpiredJwtException e) {
                log.info("Token expired during logout: {}", e.getMessage());
                handleLogoutError(response, HttpServletResponse.SC_UNAUTHORIZED, "Session has expired. Please log in again.");
                return; // 메서드를 빠져나와 추가 처리를 중단합니다.
            } catch (MalformedJwtException e) {
                log.error("Invalid JWT Token format: {}", e.getMessage());
                handleLogoutError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token format.");
                return; // 메서드를 빠져나와 추가 처리를 중단합니다.
            } catch (Exception e) {
                log.error("JWT Token validation error: {}", e.getMessage());
                handleLogoutError(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT Token validation error.");
                return; // 메서드를 빠져나와 추가 처리를 중단합니다.
            }

            // 만료 여부와 상관없이 사용자 정보를 조회하여 로그아웃 처리를 합니다.
            String userName = jwtUtil.getUserEmailFromToken(token);
            log.info("Logged out user: {}", userName);
            Optional<User> userOptional = userService.findByEmail(userName);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // 카카오 로그아웃 처리
                if ("kakao".equals(user.getLoginType())) {
                    String kakaoAccessToken = user.getSnsAccessToken(); // 저장된 카카오 액세스 토큰 사용
                    log.info("Kakao access token: {}", kakaoAccessToken);
                    String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + kakaoAccessToken);

                    HttpEntity<String> kakaoRequestEntity = new HttpEntity<>(headers);
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> kakaoResponse = restTemplate.exchange(kakaoLogoutUrl, HttpMethod.POST, kakaoRequestEntity, String.class);
                    log.info("Kakao logout response = {}", kakaoResponse.getBody());
                }

                // 토큰 삭제 처리
                Optional<TokenLogin> tokenLoginOptional = tokenLoginService.findByUserUserId(user.getUserId());
                if (tokenLoginOptional.isPresent()) {
                    tokenLoginService.deleteByRefreshToken(tokenLoginOptional.get().getRefreshToken());
                    log.info("Deleted token for user: {}", user.getUserId());
                }
            }

            // 성공적인 로그아웃 응답을 설정합니다.
            response.setStatus(HttpServletResponse.SC_OK);
            log.info("Logged out successfully");
        } else {
            // Authorization 헤더가 없거나 잘못된 경우 처리
            handleLogoutError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid Authorization header.");
        }
    }

    /**
     * 로그아웃 오류 응답을 설정하는 헬퍼 메서드
     *
     * @param response HTTP 응답 객체
     * @param status   HTTP 상태 코드
     * @param message  오류 메시지
     */
    private void handleLogoutError(HttpServletResponse response, int status, String message) {
        response.setStatus(status);
        response.setContentType("application/json");
        try {
            response.getWriter().write("{\"error\":\"" + message + "\"}");
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("Error writing to response", e);
        }
    }
}
