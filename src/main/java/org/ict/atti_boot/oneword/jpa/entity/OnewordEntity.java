package org.ict.atti_boot.oneword.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.oneword.model.dto.OnewordDto;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="ONEWORD")
@Entity
public class OnewordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ONEWORD_OW_NUM")
    @SequenceGenerator(name = "SEQ_ONEWORD_OW_NUM", sequenceName = "SEQ_ONEWORD_OW_NUM", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)  //primary key 지정하는 어노테이션(자동 채번)
    @Column(name="OW_NUM")
    private int owNum;

    @Column(name="OWSJ_NUM", nullable = false)
    private int owsjNum;

    @Column(name="OW_CONTENT", nullable = false, length = 2000)
    private String owContent;

    @Column(name="OW_RCOUNT")
    private int owRcount;

    @Column(name=" OW_WRITER", nullable = false, length = 50)
    private String owWriter;

    @Column(name="OW_WRITE_DATE")
    private Date owWriteDate;

    @PrePersist     //// jpa 로 넘어가기 전에 작동하라는 어노테이션임(insert)
    public void prePersist(){
        owWriteDate = new Date(System.currentTimeMillis());   /// 현재 날짜, 시간 적용
    }

    @PreUpdate //// jpa 로 넘어가기 전에 작동하라는 어노테이션임(update)
    public void preUpdate() {
        owWriteDate = new Date(System.currentTimeMillis());
    }

    public OnewordDto toDto(){
        return OnewordDto.builder()
                .owNum(owNum)
                .owsjNum(owsjNum)
                .owContent(owContent)
                .owRcount(owRcount)
                .owWriter(owWriter)
                .owWriteDate(owWriteDate.toString())
                .build();
    }

}
