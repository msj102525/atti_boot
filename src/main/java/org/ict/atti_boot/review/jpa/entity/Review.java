package org.ict.atti_boot.review.jpa.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @SequenceGenerator(name = "review_seq", sequenceName = "REVIEW_SEQUENCE", allocationSize = 1)
    @Column(name = "REVIEW_ID", length = 50)
    private Long  reviewId;

    @Column(name="WRITE_DATE")
    private Date writeDate;

    @Column(name="STAR_POINT")
    private int starPoint;

    @Column(name="CONTENT")
    private String content;

    @Column(name="DOCTOR_ID")
    private String doctorId;

    @Column(name="USER_ID")
    private String userId;

    public int getStarPoint() {
        return starPoint;
    }
}
