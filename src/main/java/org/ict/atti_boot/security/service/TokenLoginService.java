package org.ict.atti_boot.security.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.ict.atti_boot.security.repository.TokenLoginRepository;
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
}
