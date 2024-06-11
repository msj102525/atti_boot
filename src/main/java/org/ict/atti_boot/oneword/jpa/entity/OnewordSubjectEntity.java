package org.ict.atti_boot.oneword.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="ONEWORDSUBJECT")
@Entity    //jpa 가 관리함, repository 와 연결됨
public class OnewordSubjectEntity {
    @Id     //JPA 가 객체를 관리할 때 식별할 기본키 지정하는 어노테이션임
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //primary key 지정하는 어노테이션(자동 채번)
    @Column(name="OWSJ_NUM")
    private int owsjNum;

    @Column(name="OWSJ_SUBJECT", nullable = false, length = 500)
    private String owsjSubject;

    @Column(name="OWSJ_WRITER", nullable = false, length = 500)
    private String owsjWriter;

    @Column(name="OWSJ_WRITE_DATE")
    private Date owsjWriteDate;

}
