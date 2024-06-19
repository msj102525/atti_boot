package org.ict.atti_boot.doctor.jpa.repository;

import org.ict.atti_boot.doctor.jpa.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CareerRepository extends JpaRepository<Career, String>{
    List<Career> findByUserId(String userId);
    Career findByUserIdAndCareer(String userId, String career);
    Set<Career> findAllByUserId(String userId);
}
