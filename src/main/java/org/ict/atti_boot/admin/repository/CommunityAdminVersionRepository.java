package org.ict.atti_boot.admin.repository;

import org.ict.atti_boot.admin.model.entity.CommunityAdminVersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityAdminVersionRepository extends JpaRepository<CommunityAdminVersionEntity, Long> {

    // 회원 아이디로 검색
    Page<CommunityAdminVersionEntity> findByUserId(String userId, Pageable pageable);

    // 내용으로 검색
    Page<CommunityAdminVersionEntity> findByFeedContent(String feedContent, Pageable pageable);

}
