package org.ict.atti_boot.user.jpa.repository;

import org.ict.atti_boot.user.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String username);
    //Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmailAndLoginType(String email, String loginType);


}
