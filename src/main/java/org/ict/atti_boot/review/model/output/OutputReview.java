package org.ict.atti_boot.review.model.output;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class OutputReview {
    private String nickName;
    private String content;
    private int starPoint;
}

