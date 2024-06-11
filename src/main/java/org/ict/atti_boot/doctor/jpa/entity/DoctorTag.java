package org.ict.atti_boot.doctor.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.doctor.jpa.embedded.DoctorTagId;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DOCTOR_TAG")
public class DoctorTag implements Serializable {

    @Id
    @Column(name = "TAG", length = 50)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private Set<Doctor> doctors;
}
