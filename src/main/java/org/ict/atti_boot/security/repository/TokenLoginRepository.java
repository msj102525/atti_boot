package org.ict.atti_boot.security.repository;

import org.ict.atti_boot.security.model.entity.TokenLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenLoginRepository extends JpaRepository<TokenLogin, String> {

    Optional<TokenLogin> findByRefreshToken(String refreshToken);

    boolean existsByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

//    @Query("select rt from User r left join TokenLogin rt on r.userId = rt.user.userId left join TokenLogin tl on r.userId = tl.user.userId where r.userId = :userId")
//    Optional<TokenLogin> findByUserUserId(String userId);

    @Query("SELECT tl FROM TokenLogin tl LEFT JOIN FETCH tl.user WHERE tl.user.userId = :userId")
    Optional<TokenLogin> findByUserUserId(@Param("userId") String userId);


    }

