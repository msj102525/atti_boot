package org.ict.atti_boot.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InquiryAdminVersionDto {

    private Long inquiryNo;
    private String userId;
    private String title;
    private String content;
}
