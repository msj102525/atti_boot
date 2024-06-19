package org.ict.atti_boot.doctor.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.doctor.jpa.entity.Career;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@Component
public class DoctorUpdateInput {
    private String doctorId;
    private String hospitalName;
    private String introduce;
    private String hospitalPhone;
    private String address;
    private String zonecode;
    private String extraAddress;
    private String detailAddress;
    private Double latitude;
    private Double longitude;
    private Set<String> addCareerList;
    private Set<String> deleteCareerList;
    private Set<String> addEducationList;
    private Set<String> deleteEducationList;
    private Set<String> addTagList;
    private Set<String> deleteTagList;
}
