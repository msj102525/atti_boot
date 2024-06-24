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
@Table(name = "FAQ") //수정하기
public class FaqAdminVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAQ_NUM", nullable = false) // 컬럼명과 속성을 맞추어야 함
    private Long faqNum;

    @Column(name = "FAQ_TITLE", length = 100)
    private String faqTitle;

    @Column(name = "FAQ_WRITER", length = 50)
    private String faqWriter;

    @Column(name = "FAQ_CONTENT", length = 2000)
    private String faqContent;

    @Column(name = "FAQ_CATEGORY", length = 50)
    private String faqCategory;

}
