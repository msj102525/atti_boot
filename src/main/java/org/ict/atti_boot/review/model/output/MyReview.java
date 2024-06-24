package org.ict.atti_boot.review.model.output;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.review.jpa.entity.Review;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@NoArgsConstructor
@Component
public class MyReview {
    //리뷰정보
    private long id;
    private Date writeDate;
    private int starPoint;
    private String content;
    //의사정보
    private String doctorId;
    private String doctor;
    private String profileUrl;

    public MyReview(Review review) {
        this.id = review.getReviewId();
        this.writeDate = review.getWriteDate();
        this.starPoint = review.getStarPoint();
        this.content = review.getContent();
        this.doctorId = review.getDoctor().getUserId();
        this.doctor = review.getDoctor().getUser().getUserName();
        this.profileUrl = review.getDoctor().getUser().getProfileUrl();
    }

}
