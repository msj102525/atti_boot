package org.ict.atti_boot.doctor.model.outputVo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.doctor.jpa.entity.Career;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;
import org.ict.atti_boot.doctor.jpa.entity.Education;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Component
public class DoctorUpdateVo {
    private String address;
    private String zonecode;
    private String hospitalName;
    private String extraAddress;
    private String detailAddress;
    private String hospitalPhone;
    private String introduce;
    private List<String> careers;
    private List<String>  educations;
    private List<String> selectedTags;
    private String hospitalFileName;

    public DoctorUpdateVo(Doctor doctor) {
        this.address = doctor.getHospitalAddress();
        this.hospitalName = doctor.getHospitalName();
        this.zonecode = doctor.getPostalCode();
        this.extraAddress = doctor.getDetailAddress();
        this.detailAddress = doctor.getRemainingAddress();
        this.hospitalPhone = doctor.getHospitalPhone();
        this.introduce = doctor.getIntroduce();
        Set<Career> careers=  doctor.getCareers();
        List<String> careerList = new ArrayList<>();
        for (Career career : careers) {
            careerList.add(career.getCareer());
        }
        this.careers = careerList;
        Set<Education> educations= doctor.getEducations();
        List<String> educationList = new ArrayList<>();
        for (Education education : educations) {
            educationList.add(education.getEducation());
        }
        this.educations = educationList;
        Set<DoctorTag> tags= doctor.getTags();
        List<String> tagList = new ArrayList<>();
        for (DoctorTag tag : tags) {
            tagList.add(tag.getTag());
        }
        this.selectedTags = tagList;
    }
}
