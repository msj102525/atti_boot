package org.ict.atti_boot.admin.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INQUIRY") //수정하기
public class InquiryAdminVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INQUIRY_NO", nullable = false) // 컬럼명과 속성을 맞추어야 함
    private Long inquiryNo;

    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "TITLE", length = 250, nullable = false)
    private String title;

    @Column(name = "CONTENT", length = 3000, nullable = false)
    private String content;

}
