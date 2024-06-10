package org.ict.atti_boot.doctor.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.doctor.model.dto.DoctorDto;
import org.ict.atti_boot.user.jpa.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "DOCTOR")
@Entity
public class Doctor {

    @Id
    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "HOSPITAL_PHONE", length = 50)
    private String hospitalPhone;

    @Column(name = "INTRODUCE", length = 500)
    private String introduce;

    @Column(name = "HOSPITAL_ADDRESS", length = 150)
    private String hospitalAddress;

    @Column(name = "HOSPITAL_NAME", length = 50)
    private String hospitalName;
    // 유저와의 1:1 관계 설정
    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_ID")
    private User user;


    public DoctorDto toDto(){
        return DoctorDto.builder()
                .userId(this.userId)
                .hospitalPhone(this.hospitalPhone)
                .introduce(this.introduce)
                .hospitalAddress(this.hospitalAddress)
                .hospitalName(this.hospitalName)
        .build();
    }

}
