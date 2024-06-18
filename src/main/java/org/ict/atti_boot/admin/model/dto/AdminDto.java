package org.ict.atti_boot.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    private String userId;
    private String userName;
    private String nickName;
    private String email;



}
