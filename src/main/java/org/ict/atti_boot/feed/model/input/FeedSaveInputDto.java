package org.ict.atti_boot.feed.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedSaveInputDto {
    @NotBlank(message = "feed content cannot be empty")
    @Size(max=500, message = "content cannot exceed 500 length")
    private String feedContent;

    @NotBlank(message = "isPublic cannot be empty")
    private String inPublic;

    @NotBlank(message = "category cannot be empty")
    private String category;
}
