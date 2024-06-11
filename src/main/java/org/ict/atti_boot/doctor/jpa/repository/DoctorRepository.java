package org.ict.atti_boot.doctor.jpa.repository;

import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.model.dto.DoctorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Doctor 엔티티와 Users 엔티티를 왼쪽 조인하여 검색하는 쿼리 메서드
    @Query("SELECT d FROM Doctor d LEFT JOIN d.user u")
    List<DoctorDto> findAllWithUsers();

    Doctor findByUserId(String id);
}
