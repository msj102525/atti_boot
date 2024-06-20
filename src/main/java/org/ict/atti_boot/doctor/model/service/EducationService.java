package org.ict.atti_boot.doctor.model.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.jpa.entity.Education;
import org.ict.atti_boot.doctor.jpa.repository.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@Slf4j
public class EducationService {
    private EducationRepository educationRepository;
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public Set<Education> getEducationsById(String doctorId){
        return educationRepository.findByUserId(doctorId);
    }
}
