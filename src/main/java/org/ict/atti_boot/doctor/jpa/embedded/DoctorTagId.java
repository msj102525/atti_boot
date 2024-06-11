package org.ict.atti_boot.doctor.jpa.embedded;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;

@Data
@Embeddable
public class DoctorTagId implements Serializable {

    @ManyToMany
    @JoinColumn(name = "DOCTOR_USER_ID", referencedColumnName = "USER_ID")
    private List<Doctor> doctors;

    @Column(name = "TAG", length = 50)
    private String tag;

}
