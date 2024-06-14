package org.ict.atti_boot.review.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DOCTOR_REVIEW")
public class reviewEntity {

    @Id
    @Column(name = "TAG", length = 50)
    private String reviewId;
    private Date writeDate;

}
