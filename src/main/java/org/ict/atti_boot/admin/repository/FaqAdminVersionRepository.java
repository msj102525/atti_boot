package org.ict.atti_boot.admin.repository;

import org.ict.atti_boot.admin.model.entity.FaqAdminVersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqAdminVersionRepository extends JpaRepository<FaqAdminVersionEntity, Long> {

    // 회원 아이디로 검색
    Page<FaqAdminVersionEntity> findByFaqWriter(String faqWriter, Pageable pageable);

    // 내용으로 검색
    Page<FaqAdminVersionEntity> findByFaqContent(String faqContent, Pageable pageable);

}
