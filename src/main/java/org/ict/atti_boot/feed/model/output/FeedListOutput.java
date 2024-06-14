package org.ict.atti_boot.feed.model.output;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class FeedListOutput {

    @NotBlank(message = "feedWriterId output cannot be empty")
    private String feedWriterId;

    private String feedWriterProfileUrl;

    @NotBlank(message = "category output cannot be empty")
    private String category;

    @NotBlank(message = "feedNum output cannot be empty")
    private int feedNum;

    @NotBlank(message = "feedContent output cannot be empty")
    private String feedContent;

    @NotBlank(message = "feedWriterId output cannot be empty")
    private LocalDateTime feedDate;

    @NotBlank(message = "inPublic output cannot be empty")
    private String inPublic;

    @NotBlank(message = "replyCount output cannot be empty")
    private int replyCount;

    @NotBlank(message = "loginUserIsLiked output cannot be empty")
    private boolean loginUserIsLiked;

    @NotBlank(message = "isDocterComment output cannot be empty")
    private boolean isDocterComment;

    private String docterName;

    private String docterImgUrl;

    private String docterComment;

}
