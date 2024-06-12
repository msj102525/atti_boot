package org.ict.atti_boot.security.repository;

import org.ict.atti_boot.security.model.entity.RefreshToken;
import org.ict.atti_boot.user.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, UUID>  {

    @Query("select rt from User r left join RefreshToken rt on r.userId = rt.user.userId left join TokenLogin tl on r.userId = tl.user.userId where r.userId = :userId")
    Optional<RefreshToken> findByUserUserId(String userId);

    Optional<RefreshToken> findByTokenValue(String tokenValue);

    Boolean existsByTokenValue(String refresh);

    void deleteByTokenValue(String refresh);

   // Optional<RefreshToken> findByUserId(UUID id);


}
