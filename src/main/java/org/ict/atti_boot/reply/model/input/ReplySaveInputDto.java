package org.ict.atti_boot.reply.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveInputDto {

    private int replyNum;

    @NotBlank(message = "feedNum cannot be empty")
    private int feedNum;

    @NotBlank(message = "replyContent cannot be empty")
    private String replyContent;

    private int replySeq;

}
