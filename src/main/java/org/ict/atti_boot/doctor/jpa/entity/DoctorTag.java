package org.ict.atti_boot.doctor.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.doctor.jpa.embedded.DoctorTagId;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DOCTOR_TAG")
public class DoctorTag{

    @Id
    @Column(name = "TAG", length = 50)
    private String tag;

    @Column(name="user_Id")
    private String userId;

    public DoctorTag(String tag, String userId){
        this.tag = tag;
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorTag that = (DoctorTag) o;
        return Objects.equals(tag, that.tag) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, userId);
    }
}
