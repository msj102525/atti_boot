package org.ict.atti_boot.faq.jpa.repository;

import org.ict.atti_boot.faq.jpa.entity.FAQEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQEntity, Integer> {
    Page<FAQEntity> findAll(Pageable pageable);
}
