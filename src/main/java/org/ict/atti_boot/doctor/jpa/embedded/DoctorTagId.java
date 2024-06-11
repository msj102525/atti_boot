package org.ict.atti_boot.doctor.jpa.embedded;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;

@Data
@Embeddable
public class DoctorTagId implements Serializable {

    @ManyToOne
    @MapsId("userId") // 복합 키의 일부를 참조하여 매핑
    @JoinColumn(name = "USER_ID")
    private Doctor doctor;

    @Column(name = "TAG", length = 50)
    private String tag;

}
