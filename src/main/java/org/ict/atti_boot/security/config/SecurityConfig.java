package org.ict.atti_boot.security.config;

// Spring Framework 설정 관련 클래스들을 import 합니다.

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.repository.SuspensionRepository;
import org.ict.atti_boot.security.handler.CustomLogoutHandler;
import org.ict.atti_boot.security.jwt.filter.JWTFilter;
import org.ict.atti_boot.security.jwt.filter.LoginFilter;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.security.service.TokenLoginService;

import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration // 스프링의 설정 정보를 담는 클래스임을 나타내는 어노테이션입니다.
@EnableWebSecurity // 스프링 시큐리티 설정을 활성화합니다.
public class SecurityConfig {
    private final UserService userService;
    private final TokenLoginService tokenLoginService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final SuspensionRepository suspensionRepository;

    // 생성자를 통한 의존성 주입으로, 필요한 서비스와 설정을 초기화합니다.
    public SecurityConfig(UserService userService,CustomLogoutHandler customLogoutHandler, TokenLoginService tokenLoginService, AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, SuspensionRepository suspensionRepository) {
        this.userService = userService;
        this.tokenLoginService = tokenLoginService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.suspensionRepository = suspensionRepository;
    }

    // 인증 관리자를 스프링 컨테이너에 Bean으로 등록합니다. 인증 과정에서 중요한 역할을 합니다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // HTTP 보안 관련 설정을 정의합니다.
    // SecurityFilterChain Bean을 등록하여 HTTP 요청에 대한 보안을 구성합니다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
        http
                // CSRF, Form Login, Http Basic 인증을 비활성화합니다.
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // HTTP 요청에 대한 접근 권한을 설정합니다.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN") // '/notice' 경로에 대한 POST 요청은 ADMIN 역할을 가진 사용자만 가능합니다.
                        .requestMatchers(
                                "/users/**", "/login","/reissue","/auth/**",
                                "/file/**",
                                "/notice/**",
                                "/doctor/**",
                                "/feed/**",
                                "/board/**",
                                "/pay/**",
                                "/faq/**",
                                "/oneword/**",
                                "/onewordsubject/**",
                                "/review/**",
                                "/like/**",
                                "/reply/**",
                                "/images/**",
                                "/chat/**",
                                "/profile/**",
                                "inquiry/**",
                                "reissue/**"
                        )
                        .permitAll() // 해당 경로들은 인증 없이 접근 가능합니다.
                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN") // '/admin/**' 경로는 ADMIN 역할을 가진 사용자만 접근 가능합니다
                        .anyRequest().authenticated()) // 그 외의 모든 요청은 인증을 요구합니다.
                // JWTFilter와 LoginFilter를 필터 체인에 등록합니다.
                .addFilterBefore(new JWTFilter(jwtUtil, suspensionRepository), LoginFilter.class)
                .addFilterAt(new LoginFilter(userService, tokenLoginService, authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // 로그아웃 처리를 커스터마이징합니다.
                .logout(logout -> logout
                        .addLogoutHandler(new CustomLogoutHandler(jwtUtil, tokenLoginService, userService))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }))
                // 세션 정책을 STATELESS로 설정하여, 세션을 사용하지 않는다는 것을 명시합니다.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
