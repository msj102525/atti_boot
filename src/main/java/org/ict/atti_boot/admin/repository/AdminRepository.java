package org.ict.atti_boot.admin.repository;

import org.ict.atti_boot.admin.model.dto.AdminDto;
import org.ict.atti_boot.admin.model.entity.AdminEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, String> {

//    List<AdminEntity> findByUserId(String userId);

    @Query(value = "SELECT USER_ID, USER_NAME, NICK_NAME, EMAIL FROM USERS", nativeQuery = true)
    List<AdminDto> findAllUsersNative();

    // 회원 아이디로 검색
    Page<AdminEntity> findByUserId(String userId, Pageable pageable);

    // 회원 이름으로 검색
    Page<AdminEntity> findByUserName(String userName, Pageable pageable);





}

