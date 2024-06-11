package org.ict.atti_boot.doctor.jpa.repository;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.model.dto.DoctorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Doctor 엔티티와 Users 엔티티를 왼쪽 조인하여 검색하는 쿼리 메서드
    @Query("SELECT d FROM Doctor d LEFT JOIN d.user u")
    List<DoctorDto> findAllWithUsers();

    @Query("SELECT d FROM Doctor d JOIN d.user u WHERE u.userName LIKE %:name%")
    Page<Doctor> findByUserNameContaining(@Param("name") String name, Pageable pageable);

    @Query("SELECT d FROM Doctor d JOIN d.tags t WHERE t.id IN :tags")
    Page<Doctor> findByTagsIn(@Param("tags") List<String> tags, Pageable pageable);
    Doctor findByUserId(String id);
}
