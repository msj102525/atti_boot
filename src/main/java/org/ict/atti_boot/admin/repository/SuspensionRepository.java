package org.ict.atti_boot.admin.repository;

import org.ict.atti_boot.admin.model.entity.SuspensionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuspensionRepository extends JpaRepository<SuspensionEntity, Long> {

    Optional<SuspensionEntity> findByUserIdAndSuspensionStatus(String userId, String suspensionStatus);

    // 회원 아이디로 검색
    Page<SuspensionEntity> findByUserId(String userId, Pageable pageable);

    // 정지 사유로 검색
    Page<SuspensionEntity> findBySuspensionTitle(String suspensionTitle, Pageable pageable);
}
