package org.ict.atti_boot.security.repository;

import org.ict.atti_boot.security.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, UUID>  {
    Optional<RefreshToken> findByUserUserId(String userId);
   // Optional<RefreshToken> findByUserId(UUID Id);

    Optional<RefreshToken> findByTokenValue(String tokenValue);

    Boolean existsByTokenValue(String refresh);

    void deleteByTokenValue(String refresh);

}
