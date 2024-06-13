package org.ict.atti_boot.doctor.model.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String email;
    private String code;
    private Long numberOfMembers;
    private String doctorName;
}
