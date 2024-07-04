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

        // Authorization 헤더에서 토큰을 추출
        String authorization = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        log.info("requestURI={}", requestURI);

        // 특정 URI 및 메서드에 대해 필터링을 건너뛰도록 설정
        if (skipFiltering(requestURI, requestMethod)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우 필터링을 건너뜀
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 문자열을 제거하여 실제 토큰을 추출
        String token = authorization.substring(7);

        try {
            // 토큰에서 모든 클레임을 추출
            Claims claims = jwtUtil.getAllClaimsFromToken(token);

            // 토큰이 만료되었는지 확인
            if (jwtUtil.isTokenExpired(token)) {
                throw new ExpiredJwtException(null, claims, "Token expired");
            }

            log.info("JWT Claims: {}", claims);

            // 토큰의 카테고리를 확인
            String category = jwtUtil.getCategoryFromToken(token);
            if (category == null || !category.equals("access")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 토큰에서 사용자 이메일, 관리자 여부, 사용자 ID 추출
            String username = jwtUtil.getUserEmailFromToken(token);
            boolean is_admin = jwtUtil.isAdminFromToken(token);
            String userRealId = jwtUtil.getUserIdFromToken(token);

            // 사용자 정보를 설정
            User user = new User();
            user.setEmail(username);
            user.setUserId(userRealId);
            user.setPassword("tempPassword");
            user.setUserType(String.valueOf(user.getUserType()));

            // 사용자 상세 정보를 생성하고 인증 객체를 설정
            CustomUserDetails customUserDetails = new CustomUserDetails(user, is_admin, suspensionRepository);
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // 다음 필터로 요청을 전달
            filterChain.doFilter(request, response);

            log.info(username);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 새로운 토큰을 재발급 시도
            log.info("토큰이 만료되었습니다. 재발급을 시도합니다.");
            String newToken = reissueToken(request, response, token);
            if (newToken != null) {
                response.setHeader("Authorization", "Bearer " + newToken);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("유효하지 않은 토큰", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    // 특정 URI 및 메서드에 대해 필터링을 건너뛰도록 설정하는 메서드
    private boolean skipFiltering(String requestURI, String requestMethod) {
        return "/users/signup".equals(requestURI) ||  // 회원가입 요청
                requestURI.startsWith("/images") ||    // 이미지 요청
                (requestURI.equals("/review") && requestMethod.equals("GET")) ||  // 리뷰 조회 요청
                (requestURI.startsWith("/doctor") && !requestURI.equals("/doctor/mypage")) ||  // 의사 관련 요청, 단 /doctor/mypage는 제외
                requestURI.startsWith("/feed") ||  // 피드 요청
                requestURI.startsWith("/like") ||  // 좋아요 요청
                requestURI.startsWith("/reply") ||  // 댓글 요청
                requestURI.startsWith("/onewordsubject") ||  // 한마디 주제 요청
                requestURI.startsWith("/file") ||  // 파일 요청
                requestURI.equals("/auth/signUp") ||  // 회원가입 인증 요청
                requestURI.equals("/auth/kakao/callback") ||  // 카카오 인증 콜백
                requestURI.equals("/auth/naver/callback") ||  // 네이버 인증 콜백
                requestURI.equals("/profile") ||    // 프로필 사진
                requestURI.equals("/reissue"); // 토큰 재발급 요청
//                requestURI.equals("/inquiry");
    }

    // 만료된 토큰을 재발급받는 메서드
    private String reissueToken(HttpServletRequest request, HttpServletResponse response, String expiredToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + expiredToken); // 만료된 토큰을 헤더에 설정
        headers.set("Token", "Bearer " + expiredToken); // 만료된 액세스 토큰을 헤더에 설정
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // 재발급 엔드포인트로 POST 요청을 보냄
            ResponseEntity<String> reissueResponse = restTemplate.exchange(
//                    "http://localhost:8080/reissue", // 재발급 엔드포인트
                    "43.202.66.137:3000/reissue",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // 응답이 성공적이면 새로운 토큰을 반환
            if (reissueResponse.getStatusCode() == HttpStatus.OK) {
                log.info("응답이 성공적이면 새로운 토큰을 반환", HttpStatus.OK);
                return reissueResponse.getHeaders().getFirst("Authorization");
            }
        } catch (HttpClientErrorException e) {
            log.error("Token reissue error: {}", e.getMessage());
        }
        // 토큰 재발급 실패 시 오류 메시지 반환
        return null;
    }
}
