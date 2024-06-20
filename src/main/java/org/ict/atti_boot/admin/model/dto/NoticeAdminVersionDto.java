package org.ict.atti_boot.admin.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeAdminVersionDto {

    private Long boardNum;
    private String boardTitle;
    private String boardWriter;
    private String boardContent;

}
