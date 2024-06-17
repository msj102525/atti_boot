package org.ict.atti_boot.doctor.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.doctor.model.outputVo.DoctorDto;
import org.ict.atti_boot.user.jpa.entity.User;

import java.util.Set;

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

    @ManyToMany
    @JoinTable(
        name = "DOCTOR_TAG",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "TAG")
    )
     private Set<DoctorTag> tags;


    @ManyToMany
    @JoinTable(
        name = "CAREER",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "CAREER")
    )
     private Set<Career> careers;


    @ManyToMany
    @JoinTable(
        name = "EDUCATION",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "EDUCATION")
    )
     private Set<Education> educations;

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
