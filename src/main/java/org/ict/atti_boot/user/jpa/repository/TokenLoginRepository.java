package org.ict.atti_boot.user.jpa.repository;

import org.ict.atti_boot.user.jpa.entity.TokenLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenLoginRepository extends JpaRepository<TokenLogin, String> {

}
