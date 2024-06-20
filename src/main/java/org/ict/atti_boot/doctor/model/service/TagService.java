package org.ict.atti_boot.doctor.model.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;
import org.ict.atti_boot.doctor.jpa.repository.DoctorTagRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@Slf4j
public class TagService {
    private DoctorTagRepository doctorTagRepository;
    public TagService(DoctorTagRepository doctorTagRepository) {
        this.doctorTagRepository = doctorTagRepository;
    }

    public Set<DoctorTag> getTagsById(String doctorId){
       return doctorTagRepository.findByUserId(doctorId);
    }


}
