package org.ict.atti_boot.doctor.model.outputVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorMainVO {
    private Doctor doctor;
    private double averageScore;
}
