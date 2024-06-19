package org.ict.atti_boot.feed.model.output;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class FeedListOutput {

//    @NotBlank(message = "totalPage output caant be empty")
    private int totalPage;

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

    @NotBlank(message = "likeCount output cannot be empty")
    private int likeCount;

    @NotBlank(message = "loginUserIsLiked output cannot be empty")
    private boolean loginUserIsLiked;

    @NotBlank(message = "isDocterComment output cannot be empty")
    private boolean dComentExist;

    private String docterName;

    private String docterImgUrl;

    private String docterComment;

}
