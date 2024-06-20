package org.ict.atti_boot.doctor.jpa.repository;

import org.ict.atti_boot.doctor.jpa.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CareerRepository extends JpaRepository<Career, UUID>{
    Set<Career> findByUserId(String userId);
    Career findByUserIdAndCareer(String userId, String career);
    Set<Career> findAllByUserId(String userId);
}
