package org.ict.atti_boot.pay.jpa.repository;

import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PayRepository extends JpaRepository<PayEntity, String> {

    @Query("SELECT p FROM PayEntity p WHERE p.userId = :userId AND p.payDate BETWEEN :startTime AND :endTime")
    List<PayEntity> findRecentPayments(@Param("userId") String userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT p FROM PayEntity p ORDER BY p.payDate DESC")
    Page<PayEntity> findAll(Pageable pageable);

    Page<PayEntity> findAllByUserId(String userId, Pageable pageable);

    Page<PayEntity> findAllByPayMethod(String payMethod, Pageable pageable);

    Page<PayEntity> findAllByPayDateBetween(LocalDateTime beginDate, LocalDateTime endDate, Pageable pageable);

}
