package org.ict.atti_boot.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.springframework.stereotype.Component;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class BoardDto {

    private int boardNum;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private int readCount;
    private String boardDate;
    private int importance;
    private String filePath;
    private String fileUrl;

    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .boardNum(boardNum)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardWriter(boardWriter)
                .readCount(readCount)
                .filePath(filePath)
                .importance(importance)
                .boardDate(new java.sql.Date(System.currentTimeMillis())) // 현재 시스템 날짜로 설정
                .build();
    }
}
