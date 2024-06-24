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
@Table(name = "ONEWORDSUBJECT") //수정하기
public class OnewordAdminVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OWSJ_NUM", nullable = false) // 컬럼명과 속성을 맞추어야 함
    private Long owsjNum;

    @Column(name = "OWSJ_SUBJECT", length = 500)
    private String owsjSubject;

    @Column(name = "OWSJ_WRITER", length = 50)
    private String owsjWriter;

}
