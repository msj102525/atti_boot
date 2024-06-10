package org.ict.atti_boot.user.jpa.repository;

import jakarta.persistence.EntityManager;
import org.ict.atti_boot.user.jpa.entity.User;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;

public class UserRepositoryCustomImpl implements  UserRepositoryCustom {

    private final JpaQueryMethodFactory queryFactory;
    private final EntityManager entityManager;

    public UserRepositoryCustomImpl(JpaQueryMethodFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsername(String userId) {
        return entityManager.find(User.class, userId);
    }

}
