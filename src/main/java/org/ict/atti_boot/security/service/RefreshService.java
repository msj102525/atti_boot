package org.ict.atti_boot.security.service;


import jakarta.transaction.Transactional;
import org.ict.atti_boot.security.model.entity.RefreshToken;
import org.ict.atti_boot.security.repository.RefreshRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshService {
    private final RefreshRepository refreshRepository;

    public RefreshService(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    public void save(RefreshToken refreshToken) {
        refreshRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByTokenValue(String token) {
        return refreshRepository.findByTokenValue(token);
    }

    public Boolean existsByRefresh(String tokenValue) {
        return refreshRepository.existsByTokenValue(tokenValue);
    }

    public void deleteByRefresh(String tokenValue) {
        refreshRepository.deleteByTokenValue(tokenValue);
    }

    public Optional<RefreshToken> findByUserUserId(UUID userid) {
        return refreshRepository.findByUserUserId(String.valueOf(userid));
    }
}
