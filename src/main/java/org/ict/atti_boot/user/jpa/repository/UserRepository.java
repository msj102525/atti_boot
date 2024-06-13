package org.ict.atti_boot.user.jpa.repository;

import org.ict.atti_boot.user.jpa.entity.SocialLogin;
import org.ict.atti_boot.user.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {

     Optional<User> findByEmail(String username);
   // Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);

    @Query("select sl from SocialLogin sl left join User u on sl.userId = u.userId where sl.userId= :userId and sl.socialsite= :loginType")
    Optional<User> findByEmailAndLoginType(String userId, String loginType);    // 소셜 회원가입시 사용(카카오)

    //Optional<SocialLogin> findByUserIdAndSocialSite(String userId, String socialSite);
}


