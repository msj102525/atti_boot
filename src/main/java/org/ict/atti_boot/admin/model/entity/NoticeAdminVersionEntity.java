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
@Table(name = "BOARD") //수정하기
public class NoticeAdminVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_NUM", nullable = false) // 컬럼명과 속성을 맞추어야 함
    private Long boardNum;

    @Column(name = "BOARD_TITLE", length = 100)
    private String boardTitle;

    @Column(name = "BOARD_WRITER", length = 50)
    private String boardWriter;

    @Column(name = "BOARD_CONTENT", length = 2000)
    private String boardContent;


}
