package org.ict.atti_boot.security.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.repository.SuspensionRepository;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.output.CustomUserDetails;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final SuspensionRepository suspensionRepository;

    public JWTFilter(JWTUtil jwtUtil, SuspensionRepository suspensionRepository) {
        this.jwtUtil = jwtUtil;
        this.suspensionRepository = suspensionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JWTFilter 오류={}", request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        log.info("requestURI={}", requestURI);

        if (skipFiltering(requestURI, requestMethod)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7); // "Bearer "를 제거한 실제 토큰

        try {
            Claims claims = jwtUtil.getAllClaimsFromToken(token);
            if (jwtUtil.isTokenExpired(token)) {
                throw new ExpiredJwtException(null, claims, "Token expired");
            }
            log.info("JWT Claims: {}", claims);
            String category = jwtUtil.getCategoryFromToken(token);
            if (category == null || !category.equals("access")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String username = jwtUtil.getUserEmailFromToken(token);
            boolean is_admin = jwtUtil.isAdminFromToken(token);
            String userRealId = jwtUtil.getUserIdFromToken(token);
            User user = new User();
            user.setEmail(username);
            user.setUserId(userRealId);
            user.setPassword("tempPassword");
            user.setUserType(String.valueOf(user.getUserType()));
            CustomUserDetails customUserDetails = new CustomUserDetails(user, is_admin, suspensionRepository);
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
            log.info(username);
        } catch (ExpiredJwtException e) {
            log.info("토큰이 만료되었습니다. 재발급을 시도합니다.");
            String newToken = reissueToken(request, response, authorization);
            if (newToken != null) {
                response.setHeader("Authorization", "Bearer " + newToken);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Invalid JWT token", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean skipFiltering(String requestURI, String requestMethod) {
        return "/users/signup".equals(requestURI) || requestURI.startsWith("/images") || requestURI.equals("/review") && requestMethod.equals("GET") ||
                requestURI.startsWith("/doctor") && !requestURI.equals("/doctor/mypage") || requestURI.startsWith("/feed") ||
                requestURI.startsWith("/like") || requestURI.startsWith("/reply") || requestURI.startsWith("/onewordsubject") ||
                requestURI.startsWith("/file") || requestURI.equals("/auth/signUp") || requestURI.equals("/auth/kakao/callback") ||
                requestURI.equals("/auth/naver/callback") || requestURI.equals("/reissue");
    }

//    private String reissueToken(HttpServletRequest request, HttpServletResponse response, String expiredToken) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", expiredToken);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        try {
//            ResponseEntity<String> reissueResponse = restTemplate.exchange(
//                    "http://localhost:8080/reissue", // 재발급 엔드포인트
//                    HttpMethod.POST,
//                    entity,
//                    String.class
//            );
//
//            if (reissueResponse.getStatusCode() == HttpStatus.OK) {
//                return reissueResponse.getHeaders().getFirst("Authorization");
//            } else if (reissueResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//                log.info("Refresh token expired, logging out user");
//                SecurityContextHolder.clearContext();
//            }
//        } catch (HttpClientErrorException.NotFound e) {
//            log.error("User not found", e);
//            SecurityContextHolder.clearContext();
//        } catch (Exception e) {
//            log.error("Failed to reissue token", e);
//        }

//        return null;
private String reissueToken(HttpServletRequest request, HttpServletResponse response, String expiredToken) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + expiredToken);
    HttpEntity<String> entity = new HttpEntity<>(headers);

    try {
        ResponseEntity<String> reissueResponse = restTemplate.exchange(
                "http://localhost:8080/reissue", // 재발급 엔드포인트
                HttpMethod.POST,
                entity,
                String.class
        );

        if (reissueResponse.getStatusCode() == HttpStatus.OK) {
            return reissueResponse.getHeaders().getFirst("Authorization");
        } else if (reissueResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            log.info("Refresh token expired, logging out user");
            SecurityContextHolder.clearContext();
        }
    } catch (HttpClientErrorException.NotFound e) {
        log.error("User not found", e);
        SecurityContextHolder.clearContext();
    } catch (Exception e) {
        log.error("Failed to reissue token", e);
    }

    return null;
}

}
