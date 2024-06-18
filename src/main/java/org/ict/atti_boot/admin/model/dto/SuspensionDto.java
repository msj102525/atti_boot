package org.ict.atti_boot.admin.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuspensionDto {

    private String userId;
    private Long suspensionNo;
    private Date suspensionStart;
    private String suspensionTitle;
    private String suspensionContent;
    private String suspensionStatus;

}
