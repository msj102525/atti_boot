package org.ict.atti_boot.admin.repository;

import org.ict.atti_boot.admin.model.entity.OnewordAdminVersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnewordAdminVersionRepository extends JpaRepository<OnewordAdminVersionEntity, Long> {

    // 회원 아이디로 검색
    Page<OnewordAdminVersionEntity> findByOwsjWriter(String owsjWriter, Pageable pageable);

    // 내용으로 검색
    Page<OnewordAdminVersionEntity> findByOwsjSubject(String owsjSubject, Pageable pageable);

}
