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
@Table(name = "FEED") //수정하기
public class CommunityAdminVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEED_NUM", nullable = false) // 컬럼명과 속성을 맞추어야 함
    private Long feedNum;

    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "FEED_CONTENT", length = 1000)
    private String feedContent;


}
