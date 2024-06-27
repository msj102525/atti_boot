package org.ict.atti_boot.user.jpa.repository;

import org.ict.atti_boot.user.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserName(String userName);

    @Query("select u from User u where u.email = :email and u.loginType = :loginType")
    Optional<User> findByEmailAndLoginType(String email, String loginType);

    Optional<User> findByUserNameAndEmail(String userName, String email);

    //@Query("select sl from SocialLogin sl left join User u on sl.userId = u.userId where sl.userId= :userId and sl.socialsite= :loginType")
    //Optional<User> findByEmailAndLoginType(String userId, String loginType);    // 소셜 회원가입시 사용(카카오)

}


