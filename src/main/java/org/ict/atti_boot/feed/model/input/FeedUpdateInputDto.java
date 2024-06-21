package org.ict.atti_boot.feed.model.input;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedUpdateInputDto {
    @NotBlank(message = "feedNum cannot be empty")
    private int feedNum;

    @NotBlank(message = "feed content cannot be empty")
    @Size(max=500, message = "content cannot exceed 500 length")
    private String feedContent;

    @NotBlank(message = "isPublic cannot be empty")
    private String inPublic;

    @NotBlank(message = "category cannot be empty")
    private String category;

    private LocalDateTime feedDate;

}
