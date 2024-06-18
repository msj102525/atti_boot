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

    public JWTUtil(@Value("${jwt.secret}") String secret, UserRepository userRepository) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        this.userRepository = userRepository;
    }

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
                .claim("userName", user.get().getUserName())
                .claim("nickName", user.get().getNickName())
                .claim("profileUrl", user.get().getProfileUrl())
                .claim("userType", String.valueOf(user.get().getUserType())) // Ensure userType is a String
                .claim("gender", String.valueOf(user.get().getGender()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        log.info("generate token : " + token);
        return token;
    }

    public String getUserEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            return null;
        }
    }

//    public boolean isTokenExpired(String token) {
//        try {
//            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//            return claims.getExpiration().before(new Date());
//        } catch (JwtException | IllegalArgumentException e) {
//            log.error("Invalid JWT token", e);
//            return true;  // 예외 발생 시 만료된 것으로 간주
//        }
//    }


    public boolean isTokenExpired(String token) {
        try {
            log.debug("Received token: {}", token);  // 토큰 값 로그 출력
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            log.error("잘못된 형식의 JWT 토큰", e);
            return true;  // 잘못된 형식의 토큰은 만료된 것으로 간주
        } catch (JwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 JWT 토큰", e);
            return true;  // 기타 예외 발생 시 만료된 것으로 간주
        }
    }


    public boolean isAdminFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return Boolean.TRUE.equals(claims.get("admin", Boolean.class));
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            return false;  // 예외 발생 시 false 반환
        }
    }

    public String getCategoryFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return claims.get("category", String.class);
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT token: ", e);
            return null;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            return null;  // 예외 발생 시 null 반환
        }
    }
}
