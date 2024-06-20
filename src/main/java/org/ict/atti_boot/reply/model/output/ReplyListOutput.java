package org.ict.atti_boot.reply.model.output;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ReplyListOutput {
    @NotBlank(message = "feedNum cannot be empty")
    private int feedNum;

    @NotBlank(message = "replyNum cannot be empty")
    private int replyNum;

    @NotBlank(message = "replyReplyRef cannot be empty")
    private int replyReplyRef;

    @NotBlank(message = "replyLev cannot be empty")
    private int replyLev;

    @NotBlank(message = "replySeq cannot be empty")
    private int replySeq;

    @NotBlank(message = "replyWriter cannot be empty")
    private String replyWriter;

    @NotBlank(message = "replyUserType cannot be empty")
    private Character replyUserType;

    private String replyWriterProfileUrl;

    @NotBlank(message = "replyDate cannot be empty")
    private LocalDateTime replyDate;

    @NotBlank(message = "replyContent cannot be empty")
    private String replyContent;

    private List<ReplyListOutput> childReply;
}
