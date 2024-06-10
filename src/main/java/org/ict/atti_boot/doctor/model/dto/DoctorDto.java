package org.ict.atti_boot.doctor.model.dto;


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



    public Doctor toEntity(){
        return Doctor.builder()
                .userId(userId)
                .hospitalPhone(hospitalPhone)
                .introduce(introduce)
                .hospitalAddress(hospitalAddress)
                .hospitalName(hospitalName)
                .build();
    }


}
