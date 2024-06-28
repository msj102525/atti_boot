package org.ict.atti_boot.security.jwt.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.service.TokenLoginService;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JWTUtil {

    private final SecretKey secretKey;
    private final UserRepository userRepository;
    private final TokenLoginService tokenLoginService; // TokenLoginService 주입

    // 생성자를 통해 비밀 키와 UserRepository 및 TokenLoginService 인스턴스를 주입
    public JWTUtil(@Value("${jwt.secret}") String secret, UserRepository userRepository, TokenLoginService tokenLoginService) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        this.userRepository = userRepository;
        this.tokenLoginService = tokenLoginService;
    }

    // JWT 토큰 생성
    public String generateToken(String username, String category, Long expiredMs) {
        // 이메일을 기반으로 사용자 정보 조회
        Optional<User> user = userRepository.findByEmail(username);
        log.info("토큰생성시 이메일 조회@@@@@ :",username);
        // 사용자가 존재하지 않으면 예외 발생
        if (user.isEmpty()) {
            log.info("사용자가 존재하지 않으면 예외 발생");
            throw new UsernameNotFoundException("User with email " + username + " not found");

        }

        // 사용자가 관리자(Admin)인지 확인
        boolean isAdmin = user.get().getUserType() == 'A';

        // JWT 토큰 생성
        String token = Jwts.builder()
                .setSubject(username) // 토큰의 주체 (subject)를 사용자 이메일로 설정
                .claim("admin", isAdmin) // 관리자인지 여부를 클레임에 추가
                .claim("category", category) // 카테고리를 클레임에 추가
                .claim("userId", user.get().getUserId()) // 사용자 ID를 클레임에 추가
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간 설정 (밀리초)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 알고리즘과 비밀 키를 사용해 서명
                .compact(); // 토큰을 문자열로 직렬화

        // 생성된 토큰을 로그로 출력
        log.info("Generated token: " + token);

        // 생성된 토큰 반환
        return token;
    }


    // JWT 토큰에서 사용자 이메일 추출
    public String getUserEmailFromToken(String token) {
        log.info("토큰에서 사용자 이메일 추출: " + token);
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
            return e.getClaims().getSubject(); // 만료된 토큰에서도 사용자 이메일을 가져올 수 있도록
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            return null;
        }
    }

    // JWT 토큰에서 사용자 아이디 추출
    public String getUserIdFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = getClaimsFromToken(token);
            return claims.get("userId", String.class);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Token is expired", e);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid token signature", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

    // 토큰 만료 여부 확인 및 삭제
    public boolean isTokenExpired(String token) {
        log.info("Checking token expiration for: " + token);
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = getClaimsFromToken(token);
            boolean isExpired = claims.getExpiration().before(new Date());
            if (isExpired) {
                // 토큰이 만료되었으므로 데이터베이스에서 삭제
                tokenLoginService.deleteByRefreshToken(token);
                log.info("Expired token deleted from database");
            }
            return isExpired;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: ", e);
            // 잘못된 토큰을 데이터베이스에서 삭제
            tokenLoginService.deleteByRefreshToken(token);
            log.info("Invalid token deleted from database");
            return true; // 잘못된 토큰도 만료된 것으로 간주
        }
    }

    // 토큰에서 관리자 여부 확인
    public boolean isAdminFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = getClaimsFromToken(token);
            return Boolean.TRUE.equals(claims.get("admin", Boolean.class));
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            return false; // 예외 발생 시 false 반환
        }
    }

    // 토큰에서 카테고리 추출
    public String getCategoryFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = getClaimsFromToken(token);
            return claims.get("category", String.class);
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT token: ", e);
            return null;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            return null; // 예외 발생 시 null 반환
        }
    }

    // 모든 클레임 가져오기
    public Claims getAllClaimsFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            return getClaimsFromToken(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            return null;
        }
    }

    // 토큰에서 클레임 추출
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .setAllowedClockSkewSeconds(30) // 30초 시계 오차 허용
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
