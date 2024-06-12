package org.ict.atti_boot.doctor.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "EDUCATION")
public class Education {

    @Id
    @Column(name = "EDUCATION", length = 50)
    private String education;

    @ManyToMany(mappedBy = "educations")
    private Set<Doctor> doctors;
}
