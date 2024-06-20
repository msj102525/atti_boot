package org.ict.atti_boot.doctor.model.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.jpa.entity.Career;
import org.ict.atti_boot.doctor.jpa.repository.CareerRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@Slf4j
public class CareerService {
    private final CareerRepository careerRepository;
    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }
    public Set<Career> getCareersById(String doctorId){
        return careerRepository.findByUserId(doctorId);
    }

}
