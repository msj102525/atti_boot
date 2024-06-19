package org.ict.atti_boot.admin.repository;


import org.ict.atti_boot.admin.model.entity.NoticeAdminVersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeAdminVersionRepository extends JpaRepository<NoticeAdminVersionEntity, Long> {

    // 회원 아이디로 검색
    Page<NoticeAdminVersionEntity> findByBoardWriter(String boardWriter, Pageable pageable);

    // 내용으로 검색
    Page<NoticeAdminVersionEntity> findByBoardContent(String boardContent, Pageable pageable);




}
