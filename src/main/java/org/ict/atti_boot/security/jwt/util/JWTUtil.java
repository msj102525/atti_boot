package org.ict.atti_boot.security.jwt.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
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

    // 생성자를 통해 비밀 키와 UserRepository 인스턴스를 주입
    public JWTUtil(@Value("${jwt.secret}") String secret, UserRepository userRepository) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        this.userRepository = userRepository;
    }

    // JWT 토큰 생성
    public String generateToken(String userEmail, String category, Long expiredMs) {
        Optional<User> user = userRepository.findByEmail(userEmail);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + userEmail + " not found");
        }

        boolean isAdmin = user.get().getUserType() == 'A';

        String token = Jwts.builder()
                .setSubject(userEmail)
                .claim("admin", isAdmin)
                .claim("category", category)
                .claim("userId", user.get().getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간 (밀리초)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        log.info("Generated token: " + token);
        return token;
    }

//    // JWT 토큰에서 사용자 이메일 추출
//    public String getUserEmailFromToken(String token) {
//        log.info("Extracting user email from token: " + token);
//        try {
//            if (token.startsWith("Bearer ")) {
//                token = token.substring(7);
//            }
//
//            Claims claims = getClaimsFromToken(token);
//            return claims.getSubject();
//        } catch (ExpiredJwtException e) {
//            log.error("Token expired", e);
//            return e.getClaims().getSubject(); // 만료된 토큰에서도 사용자 이메일을 가져올 수 있도록
//        } catch (JwtException | IllegalArgumentException e) {
//            log.error("Invalid JWT token", e);
//            return null;
//        }
//    }
// JWT 토큰에서 사용자 이메일 추출
    public String getUserEmailFromToken(String token) {
        log.info("Extracting user email from token: " + token);
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

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        log.info("Checking token expiration for: " + token);
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = getClaimsFromToken(token);
            return claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: ", e);
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
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
