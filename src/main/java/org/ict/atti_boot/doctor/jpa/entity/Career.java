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
@Table(name = "CAREER")
public class Career {

    @Id
    @Column(name = "CAREER", length = 50)
    private String career;

   @Column(name="user_Id")
    private String userId;

   public Career(String career, String userId) {
       this.career = career;
       this.userId = userId;
   }

       @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Career career1 = (Career) o;
        return Objects.equals(career, career1.career) &&
                Objects.equals(userId, career1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(career, userId);
    }


}
