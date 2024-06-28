// 필요한 클래스와 인터페이스를 import합니다.
package org.ict.atti_boot.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.ict.atti_boot.security.service.TokenLoginService;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.input.InputUser;
import org.ict.atti_boot.user.model.output.CustomUserDetails;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Lombok의 @Slf4j 어노테이션을 사용하여 로깅을 간편하게 합니다.
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final Long accessExpiredMs;
    private final Long refreshExpiredMs;

    private final UserService userService;
    private final TokenLoginService tokenLoginService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(UserService userService, TokenLoginService tokenLoginService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userService = userService;
        this.tokenLoginService = tokenLoginService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshExpiredMs = 7200000L;   // 2시간
        this.accessExpiredMs = 600000L;     // 10분
//        this.accessExpiredMs = 10000L; // 10 초
//        this.refreshExpiredMs = 30000L; // 30 초

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청 본문에서 사용자의 로그인 데이터를 읽어들입니다.
            InputUser loginData = new ObjectMapper().readValue(request.getInputStream(), InputUser.class);
            log.info("loginData = {}", loginData.toString());
            // 사용자의 아이디와 비밀번호를 기반으로 인증 토큰을 생성합니다.
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginData.getUserId(), loginData.getPassword());
            log.info("Attempting to authenticate user {}", loginData.getUserId());
            // AuthenticationManager를 사용하여 인증을 시도합니다.
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            // 입력 데이터를 읽는 도중 오류가 발생한 경우 예외를 던집니다.
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 인증된 사용자 정보를 가져옵니다.
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        // 액세스 토큰과 리프레시 토큰을 생성합니다.
        String accessToken = jwtUtil.generateToken(username, "access", accessExpiredMs);
        log.info("Successfully authenticated user {}. Access Token: {}", username, accessToken);
        String refreshToken = jwtUtil.generateToken(username, "refresh", refreshExpiredMs);
        log.info("Successfully authenticated user {}. Refresh Token: {}", username, refreshToken);

        // 사용자 정보를 데이터베이스에서 조회합니다.
        Optional<User> userOptional = userService.findByEmail(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // TokenLogin 객체가 이미 존재하는지 확인합니다.
            Optional<TokenLogin> existingTokenLogin = tokenLoginService.findByUserUserId(user.getUserId());
            if (existingTokenLogin.isPresent()) {
                // 기존 TokenLogin 객체 업데이트
                TokenLogin tokenLogin = existingTokenLogin.get();
                tokenLogin.setAccessToken(accessToken);
                tokenLogin.setRefreshToken(refreshToken);
                tokenLogin.setAccessCreated(LocalDateTime.now());
                tokenLogin.setAccessExpires(LocalDateTime.now().plusSeconds(accessExpiredMs / 1000));
                tokenLogin.setRefreshCreated(LocalDateTime.now());
                tokenLogin.setRefreshExpires(LocalDateTime.now().plusSeconds(refreshExpiredMs / 1000));
                tokenLogin.setStatus("activated"); // 상태를 "activated"로 설정
                tokenLoginService.save(tokenLogin);
                log.info("Updated existing TokenLogin for user {}", username);
            } else {
                // 새로운 TokenLogin 객체 생성 및 저장
                TokenLogin tokenLogin = TokenLogin.builder()
                        .user(user)
                        .userId(user.getUserId())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .accessCreated(LocalDateTime.now())
                        .accessExpires(LocalDateTime.now().plusSeconds(accessExpiredMs / 1000))
                        .refreshCreated(LocalDateTime.now())
                        .refreshExpires(LocalDateTime.now().plusSeconds(refreshExpiredMs / 1000))
                        .status("activated") // 상태를 "activated"로 설정
                        .build();
                tokenLoginService.save(tokenLogin);
                log.info("Created new TokenLogin for user {}", username);
            }

            // 응답 헤더에 액세스 토큰을 추가합니다.
            response.addHeader("Authorization", "Bearer " + accessToken);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");

            // 응답 본문에 사용자 정보를 JSON 형식으로 추가합니다.
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("username", username);
            responseBody.put("isAdmin", customUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
            responseBody.put("refreshToken", refreshToken);
            responseBody.put("userId", user.getUserId());
            responseBody.put("userName", user.getUserName());
            responseBody.put("nickName", user.getNickName());
            responseBody.put("email", user.getEmail());
            responseBody.put("profileUrl", user.getProfileUrl());
            responseBody.put("userType", user.getUserType());
            responseBody.put("loginType", user.getLoginType());
            responseBody.put("birthDate", user.getBirthDate());
            responseBody.put("gender", user.getGender());
            responseBody.put("phone", user.getPhone());
            responseBody.put("password", user.getPassword());

            // ObjectMapper를 사용하여 JSON으로 직렬화
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String responseBodyJson = objectMapper.writeValueAsString(responseBody);
            response.setContentType("application/json");
            response.getWriter().write(responseBodyJson);
            response.getWriter().flush();

            log.info("Successfully responded with user information for user {}", username);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String message;
        if (failed instanceof UsernameNotFoundException) {
            message = "존재하지 않는 이메일입니다.";
        } else if (failed instanceof BadCredentialsException) {
            message = "잘못된 비밀번호입니다.";
        } else if (failed instanceof DisabledException) {
            message = "계정이 비활성화되었습니다.";
        } else if (failed instanceof LockedException) {
            message = "계정이 잠겨 있습니다.";
        } else {
            message = "인증에 실패했습니다.";
        }

        // 실패 응답을 JSON 형식으로 작성합니다.
        Map<String, Object> responseData = new HashMap<>();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseData.put("timestamp", LocalDateTime.now().toString());
        responseData.put("status", HttpStatus.UNAUTHORIZED.value());
        responseData.put("error", "Unauthorized");
        responseData.put("message", message);
        responseData.put("path", request.getRequestURI());

        String jsonResponse = new ObjectMapper().writeValueAsString(responseData);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
        log.info("인증 시도가 실패했습니다: {}", message);
    }
}
