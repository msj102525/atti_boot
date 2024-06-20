package org.ict.atti_boot.doctor.jpa.repository;

import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DoctorTagRepository extends JpaRepository<DoctorTag, String>, CustomDoctorTagRepository {
    Set<DoctorTag> findAllByUserId(String userId);
    DoctorTag findByUserIdAndTag(String userId, String tag);
}
