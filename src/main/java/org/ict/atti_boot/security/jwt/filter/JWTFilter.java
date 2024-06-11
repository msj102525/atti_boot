package org.ict.atti_boot.security.jwt.filter;

// 필요한 클래스와 인터페이스를 import 합니다.

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

    // 생성자를 통해 JWTUtil의 인스턴스를 주입받습니다.
    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    // 필터의 주요 로직을 구현하는 메서드입니다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JWTFilter 오류");
        // 요청에서 'Authorization' 헤더를 추출합니다.
        String authorization = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        if ("/reissue".equals(requestURI) || "/users/signup".equals(requestURI) || "/feed".startsWith(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        //의사 리스트 요청 필터 넘기기
        if (requestURI.startsWith("/doctor")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        

        // 'Authorization' 헤더가 없거나 Bearer 토큰이 아니면 요청을 계속 진행합니다.
        if( authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        // Bearer 토큰에서 JWT를 추출합니다.
        String token = authorization.split(" ")[1];

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");
            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // token에서 category 가져오기
        String category = jwtUtil.getCategoryFromToken(token);
        // 토큰 category 가 access 가 아니 라면 만료 된 토큰 이라고 판단
        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            // 응답 코드를 프론트와 맞추는 부분 401 에러 외 다른 코드로 맞춰서
            // 진행하면 리프레시 토큰 발급 체크를 빠르게 할수 있음
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        // 토큰에서 사용자 이메일과 관리자 여부를 추출합니다.
        String userId = jwtUtil.getUserEmailFromToken(token);
        boolean is_admin = jwtUtil.isAdminFromToken(token);

        // 인증에 사용할 임시 User 객체를 생성하고, 이메일과 관리자 여부를 설정합니다.
        User user = new User();
        user.setEmail(userId);
        user.setPassword("tempPassword"); // 실제 인증에서는 사용되지 않는 임시 비밀번호를 설정합니다.
        user.setUserType(user.getUserType());

        // User 객체를 기반으로 CustomUserDetails 객체를 생성합니다.
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // Spring Security의 Authentication 객체를 생성하고, SecurityContext에 설정합니다.
        // 이로써 해당 요청에 대한 사용자 인증이 완료됩니다.
        //Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        Authentication authToken = new UsernamePasswordAuthenticationToken(userId, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 필터 체인을 계속 진행합니다.
        filterChain.doFilter(request,response);
    }
}
