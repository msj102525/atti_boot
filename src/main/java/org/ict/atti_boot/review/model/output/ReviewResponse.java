package org.ict.atti_boot.review.model.output;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ReviewResponse {
    private List<OutputReview> reviewList;
    private Boolean hasMoreReview;
}
