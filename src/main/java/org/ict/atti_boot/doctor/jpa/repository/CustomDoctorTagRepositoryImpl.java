package org.ict.atti_boot.doctor.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;
import org.springframework.transaction.annotation.Transactional;

public class CustomDoctorTagRepositoryImpl implements CustomDoctorTagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void customSaveDoctorTag(DoctorTag doctorTag) {
        // Custom logic for saving DoctorTag
        if (doctorTag != null) {
            // Example logic: Merge the entity if it's managed, persist otherwise
            if (entityManager.contains(doctorTag)) {
                entityManager.merge(doctorTag);
            } else {
                entityManager.persist(doctorTag);
            }
        }
    }
}
