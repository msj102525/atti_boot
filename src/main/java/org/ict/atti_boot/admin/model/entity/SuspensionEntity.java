package org.ict.atti_boot.admin.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SUSPENSION") //수정하기
public class SuspensionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUSPENSION_NO", nullable = false) // 컬럼명과 속성을 맞추어야 함
    private Long suspensionNo;

    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "SUSPENSION_START", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date suspensionStart= new Date();

    @Column(name = "SUSPENSION_TITLE", length = 100, nullable = false)
    private String suspensionTitle;

    @Column(name = "SUSPENSION_CONTENT", length = 600)
    private String suspensionContent;

    @Column(name = "SUSPENSION_STATUS", length = 20, nullable = false)
    private String suspensionStatus= "ACTIVE";

    // 기타 필요한 필드는 여기에 추가
}
