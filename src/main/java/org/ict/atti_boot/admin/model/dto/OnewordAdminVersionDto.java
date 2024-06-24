package org.ict.atti_boot.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnewordAdminVersionDto {

    private Long owsjNum;
    private String owsjSubject;
    private String owsjWriter;

}
