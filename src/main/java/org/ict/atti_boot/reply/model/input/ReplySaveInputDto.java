package org.ict.atti_boot.reply.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplySaveInputDto {

    private int replyNum;

    @NotBlank(message = "feedNum cannot be empty")
    private int feedNum;

    @NotBlank(message = "replyContent cannot be empty")
    private String replyContent;

    private int replySeq;

}
