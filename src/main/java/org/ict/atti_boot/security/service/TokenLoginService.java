package org.ict.atti_boot.security.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.ict.atti_boot.security.repository.TokenLoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TokenLoginService {

    private final TokenLoginRepository tokenLoginRepository;

    // TokenLoginRepository를 주입받는 생성자
    public TokenLoginService(TokenLoginRepository tokenLoginRepository) {
        this.tokenLoginRepository = tokenLoginRepository;
    }

    // Refresh Token으로 TokenLogin 엔티티를 찾습니다.
    public Optional<TokenLogin> findByRefreshToken(String token) {
        return tokenLoginRepository.findByRefreshToken(token);
    }

    // Access Token으로 TokenLogin 엔티티를 찾습니다.
    public Optional<TokenLogin> findByAccessToken(String token) {
        return tokenLoginRepository.findByAccessToken(token);
    }

    // Refresh Token이 존재하는지 확인합니다.
    public Boolean existsByRefreshToken(String tokenValue) {
        return tokenLoginRepository.existsByRefreshToken(tokenValue);
    }

    // Refresh Token으로 TokenLogin 엔티티를 삭제합니다.
    public void deleteByRefreshToken(String tokenValue) {
        tokenLoginRepository.deleteByRefreshToken(tokenValue);
    }

    // 사용자 ID로 TokenLogin 엔티티를 찾습니다.
    public Optional<TokenLogin> findByUserUserId(String userId) {
        return tokenLoginRepository.findByUserUserId(userId);
    }

    // TokenLogin 엔티티를 저장합니다.
    public void save(TokenLogin tokenLogin) {
        if (tokenLogin.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        tokenLoginRepository.save(tokenLogin);
    }

    // Refresh Token의 유효성을 검사합니다.
    public ResponseEntity<String> validateRefreshToken(String refreshToken) {
        // TokenLoginRepository를 이용하여 Refresh Token으로 TokenLogin 엔티티를 조회합니다.
        Optional<TokenLogin> refreshTokenOptional = tokenLoginRepository.findByRefreshToken(refreshToken);

        // Refresh Token이 존재하지 않는 경우, HTTP 401 상태 코드와 함께 "Refresh token not found" 메시지를 반환합니다.
        return refreshTokenOptional.map(tokenLogin -> {
            // 조회된 TokenLogin 엔티티의 상태가 "activated"인지 확인합니다.
            if (!"activated".equals(tokenLogin.getStatus())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token invalid or expired");
            }
            // Refresh Token이 유효한 경우, HTTP 200 상태 코드와 함께 "Refresh token is valid" 메시지를 반환합니다.
            return ResponseEntity.ok("Refresh token is valid");
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token not found"));
    }
}
