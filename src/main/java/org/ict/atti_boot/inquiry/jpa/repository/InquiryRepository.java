package org.ict.atti_boot.inquiry.jpa.repository;

import org.ict.atti_boot.inquiry.jpa.entity.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer> {
    List<InquiryEntity> findByTitleContaining(String keyword);

    void deleteByUserId(String userId);
}
