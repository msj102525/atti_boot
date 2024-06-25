package org.ict.atti_boot.security.jwt.filter;

// 필요한 클래스와 인터페이스를 import 합니다.

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

// Lombok의 @Slf4j 어노테이션을 사용하여 로깅 기능을 자동으로 추가합니다.
@Slf4j
// Spring Security의 OncePerRequestFilter를 상속받아, 모든 요청에 대해 한 번씩 실행되는 필터를 정의합니다.
public class JWTFilter extends OncePerRequestFilter {

    // JWT 관련 유틸리티 메서드를 제공하는 JWTUtil 클래스의 인스턴스를 멤버 변수로 가집니다.
    private final JWTUtil jwtUtil;
    private final SuspensionRepository suspensionRepository;

    // 생성자를 통해 JWTUtil의 인스턴스를 주입받습니다.
    public JWTFilter(JWTUtil jwtUtil, SuspensionRepository suspensionRepository) {

        this.jwtUtil = jwtUtil;
        this.suspensionRepository = suspensionRepository;
    }

    // 필터의 주요 로직을 구현하는 메서드입니다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JWTFilter 오류={}", request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        log.info("requestURI={}", requestURI);

        if ("/reissue".equals(requestURI) || "/users/signup".equals(requestURI)) {
            filterChain.doFilter(request, response);
            log.info("/reissue={}", requestURI);
            return;
        }
        if (requestURI.startsWith("/images")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.equals("/review") && requestMethod.equals("GET")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.startsWith("/doctor") && !requestURI.equals("/doctor/mypage")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.startsWith("/feed")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.startsWith("/like")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.startsWith("/reply")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.startsWith("/onewordsubject")) {
            log.info(requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.startsWith("/file")) {
            log.info(requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.equals("/auth/signUp") || requestURI.equals("/auth/kakao/callback") || requestURI.equals("/auth/naver/callback")) {
            log.info(requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.startsWith("/inquiry")) {
            log.info(requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        try {
            jwtUtil.isTokenExpired(token);

            Claims claims = jwtUtil.getAllClaimsFromToken(token);
            log.info("JWT Claims: {}", claims);

            String category = jwtUtil.getCategoryFromToken(token);
            if (category == null || !category.equals("access")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String userId = jwtUtil.getUserEmailFromToken(token);
            boolean is_admin = jwtUtil.isAdminFromToken(token);
            String userRealId = jwtUtil.getUserIdFromToken(token);

            User user = new User();
            user.setEmail(userId);
            user.setUserId(userRealId);
            user.setPassword("tempPassword");
            user.setUserType(String.valueOf(user.getUserType()));

            CustomUserDetails customUserDetails = new CustomUserDetails(user, is_admin, suspensionRepository);
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
            log.info(userId);

        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            log.error("Invalid JWT token", e);
            filterChain.doFilter(request, response);
            return;
        }
    }
}