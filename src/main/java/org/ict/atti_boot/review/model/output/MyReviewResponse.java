package org.ict.atti_boot.review.model.output;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@Component
public class MyReviewResponse<MyReview> {
    private List<MyReview> myReviews;
    private int totalPage;


    public MyReviewResponse(List<MyReview> myReviews, int totalPage) {
        this.myReviews = myReviews;
        this.totalPage = totalPage;
    }

}
