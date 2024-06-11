package org.ict.atti_boot.feed.model.output;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class FeedSaveOutput {

    @NotBlank(message = "feedNum output cannot be empty")
    private int feedNum;

    @NotBlank(message = "userId output cannot be empty")
    private String userId;

    @NotBlank(message = "feedContent output cannot be empty")
    private String feedContent;

    @NotBlank(message = "feedDate output cannot be empty")
    private LocalDateTime feedDate;

    @NotBlank(message = "feedReadCount output cannot be empty")
    private int feedReadCount;

    @NotBlank(message = "category output cannot be empty")
    private String category;

    @NotBlank(message = "feedNum output cannot be empty")
    private String inPublic;
}
