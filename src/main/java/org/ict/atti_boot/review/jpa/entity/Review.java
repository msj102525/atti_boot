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
public class Review {

    @Id
    @Column(name = "REVIEW_ID", length = 50)
    private String reviewId;

    @Column(name="WRITE_DATE")
    private Date writeDate;

    @Column(name="STAR_POINT")
    private int startPoint;

    @Column(name="CONTENT")
    private String content;

    @Column(name="DOCTOR_ID")
    private String doctorId;

    @Column(name="USER_ID")
    private String userId;

    public int getStarPoint() {
        return startPoint;
    }
}
