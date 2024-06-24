package org.ict.atti_boot.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FaqAdminVersionDto {

    private Long faqNum;
    private String faqTitle;
    private String faqWriter;
    private String faqContent;
    private String faqCategory;

}
