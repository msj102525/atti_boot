package org.ict.atti_boot.doctor.model.outputVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class DoctorDetail {
    private String doctorId;
    private String hospitalPhone;
    private String introduce;
    private String hospitalAddress;
    private String hospitalName;
    private String userName;
    private String profileUrl;
    private List<String> careers;
    private List<String> educations;
    private List<String> tags;
    private Double averageStarPoint;




    public DoctorDetail(Doctor doctor){
        this.doctorId = doctor.getUserId();
        this.userName= doctor.getUser().getUserName();
        this.hospitalPhone = doctor.getHospitalPhone();
        this.introduce = doctor.getIntroduce();
        this.hospitalAddress = doctor.getHospitalAddress();
        this.hospitalName = doctor.getHospitalName();
    }
}

