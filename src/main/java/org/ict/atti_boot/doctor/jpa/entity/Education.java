package org.ict.atti_boot.doctor.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "EDUCATION")
public class Education {

    @Id
    @Column(name = "EDUCATION", length = 50)
    private String education;

    @Column(name="user_Id")
    private String userId;

    public Education(String education, String userId) {
        this.education = education;
        this.userId = userId;
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education that = (Education) o;
        return Objects.equals(education, that.education) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(education, userId);
    }

}
