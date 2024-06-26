package org.ict.atti_boot.board.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.board.model.dto.BoardDto;

import java.io.File;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="BOARD")
@Entity


public class BoardEntity {

    @Id
    @SequenceGenerator(name = "board_seq_gen", sequenceName = "BOARD_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_gen")  //primary key 지정하는 어노테이션(자동 채번)
    @Column(name="BOARD_NUM")
    private int boardNum;

    @Column(name="BOARD_TITLE", nullable = true)
    private String boardTitle;

    @Column(name="BOARD_Writer", nullable = true)
    private String boardWriter;

    @Column(name="BOARD_Content", nullable = true)
    private String boardContent;

    @Column(name="READ_COUNT", nullable = true)
    private int readCount;

    @Column(name="BOARD_DATE", nullable = true)
    private Date boardDate;

    @Column(name="FILEPATH")
    private String filePath;

    @Column(name="IMPORTANCE", nullable = true)
    private int importance;

    @PrePersist     //// jpa 로 넘어가기 전에 작동하라는 어노테이션임
    public void prePersist(){
        boardDate = new Date(System.currentTimeMillis());   /// 현재 날짜, 시간 적용
    }

    public BoardDto toDto(){
        return BoardDto.builder()
                .boardNum(boardNum)
                .boardTitle(boardTitle)
                .boardWriter(boardWriter)
                .boardContent(boardContent)
                .readCount(readCount)
                .fileUrl(filePath != null ? "/board/download/" + new File(filePath).getName() : null)
                .boardDate(boardDate.toString())
                .importance(importance)
                .build();
    }



}
