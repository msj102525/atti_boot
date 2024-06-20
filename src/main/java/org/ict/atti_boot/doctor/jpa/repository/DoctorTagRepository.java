package org.ict.atti_boot.doctor.jpa.repository;

import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface DoctorTagRepository extends JpaRepository<DoctorTag, UUID> {
    Set<DoctorTag> findAllByUserId(String userId);
    Set<DoctorTag> findByUserId(String userId);
    DoctorTag findByUserIdAndTag(String userId, String tag);
}
