package org.ict.atti_boot.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.ict.atti_boot.board.model.dto.BoardDto;
import org.ict.atti_boot.board.model.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@CrossOrigin
public class BoardController {

    @Autowired
    private BoardService boardService;

    private final String uploadDir = "uploads/";

    // 전체 리스트 출력
    @GetMapping("/boardList")
    public Page<BoardDto> getBoardList(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardService.selectList(pageRequest);
    }

    // 상세 보기 출력
    @GetMapping("/boardDetail/{boardNum}")
    public BoardDto getBoardDetail(@PathVariable int boardNum) {
        return boardService.getBoardDetail(boardNum);
    }

    // 삭제 처리
    @DeleteMapping("/boardDetail/{boardNum}")
    public ResponseEntity<Void> deleteBoard(@PathVariable int boardNum) {
        log.info("deleteBoard : {} ", boardNum);
        boardService.deleteBoard(boardNum);
        return ResponseEntity.noContent().build();
    }

    // 수정 처리
    @PutMapping("/boardUpdate/{boardNum}")
    public BoardDto updateBoard(@PathVariable int boardNum,
                                @RequestParam("boardTitle") String boardTitle,
                                @RequestParam("boardContent") String boardContent,
                                @RequestParam("importance") int importance,
                                @RequestParam(value = "boardWriter", required = false) String boardWriter,
                                @RequestParam(value = "readCount", required = false) int readCount,
                                @RequestParam(value = "file", required = false) MultipartFile file) {

        BoardDto boardDto = BoardDto.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .importance(importance)
                .boardWriter(boardWriter)
                .readCount(readCount)
                .build();

        return boardService.updateBoard(boardNum, boardDto, file);
    }

    // 검색 처리
    @GetMapping("/search")
    public Page<BoardEntity> getBoards(@RequestParam int page,
                                     @RequestParam int size,
                                     @RequestParam(required = false) String action,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String beginDate,
                                     @RequestParam(required = false) String endDate) {
        LocalDateTime beginDateTime = null;
        LocalDateTime endDateTime = null;

        if (beginDate != null && !beginDate.isEmpty()) {
            LocalDate begin = LocalDate.parse(beginDate, DateTimeFormatter.ISO_LOCAL_DATE);
            beginDateTime = begin.atStartOfDay();
        }

        if (endDate != null && !endDate.isEmpty()) {
            LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
            endDateTime = end.atStartOfDay().plusDays(1).minusNanos(1);  // 하루의 끝까지 포함하도록 설정
        }

        Pageable pageable = PageRequest.of(page, size);
        return boardService.getBoards(action, keyword, beginDateTime, endDateTime, pageable);
    }

    // 등록 처리
    @PostMapping
    public BoardDto createBoard(@RequestParam("boardTitle") String boardTitle,
                                @RequestParam("boardContent") String boardContent,
                                @RequestParam("importance") int importance,
                                @RequestParam("boardWriter") String boardWriter,
                                @RequestParam("readCount") int readCount,
                                @RequestParam(value = "file", required = false) MultipartFile file) {

        BoardDto boardDto = BoardDto.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .importance(importance)
                .boardWriter(boardWriter)
                .readCount(readCount)
                .build();

        return boardService.createBoard(boardDto, file);
    }


    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        return boardService.downloadFile(filename);
    }




}
