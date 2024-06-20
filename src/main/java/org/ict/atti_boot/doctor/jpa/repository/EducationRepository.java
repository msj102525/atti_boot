package org.ict.atti_boot.doctor.jpa.repository;

import org.ict.atti_boot.doctor.jpa.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EducationRepository extends JpaRepository<Education, UUID> {
    Set<Education> findByUserId(String userId);
    Set<Education> findAllByUserId(String userId);
     Education findByUserIdAndEducation(String userId, String education);
}
