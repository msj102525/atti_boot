package org.ict.atti_boot.doctor.model.outputVo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class DoctorDto {
    private String userId;
    private String hospitalPhone;
    private String introduce;
    private String hospitalAddress;
    private String hospitalName;
    private String userName;



    public DoctorDto(Doctor doctor){
        this.userId = doctor.getUserId();
        this.userName= doctor.getUser().getUserName();
        this.hospitalPhone = doctor.getHospitalPhone();
        this.introduce = doctor.getIntroduce();
        this.hospitalAddress = doctor.getHospitalAddress();
        this.hospitalName = doctor.getHospitalName();
    }
}
