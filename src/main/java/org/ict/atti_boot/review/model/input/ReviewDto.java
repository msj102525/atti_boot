package org.ict.atti_boot.review.model.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ReviewDto {

    private String reviewId;
    private int rating;
    private String review;
    private Date writeDate;
    private String senderId;
    private String receiverId;
}
