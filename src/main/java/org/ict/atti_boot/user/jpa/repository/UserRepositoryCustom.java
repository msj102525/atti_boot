package org.ict.atti_boot.user.jpa.repository;


import org.ict.atti_boot.user.jpa.entity.User;

public interface UserRepositoryCustom {
    User findByUsername(String userId);

}
