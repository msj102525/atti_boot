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
@Table(name = "CAREER")
public class Career {

    @Id
    @Column(name = "CAREER", length = 50)
    private String career;

   @Column(name="user_Id")
    private String userId;
}
