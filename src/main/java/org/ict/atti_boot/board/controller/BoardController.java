package org.ict.atti_boot.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.board.model.dto.BoardDto;
import org.ict.atti_boot.board.model.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@CrossOrigin
public class BoardController {

    @Autowired
    private BoardService boardService;

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
    public ResponseEntity<BoardDto> updateBoard(
            @PathVariable("boardNum") int boardNum,
            @RequestBody BoardDto boardDto) {

        boardService.updateBoard(boardNum, boardDto);
        return ResponseEntity.noContent().build();

    }

    // 검색 처리
    @GetMapping("/search")
    public ResponseEntity<List<BoardDto>> searchBoards(
        @RequestParam String action,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String beginDate,
        @RequestParam(required = false) String endDate
    ) {
        List<BoardDto> boards = boardService.searchBoards(action, keyword, beginDate, endDate);
        return ResponseEntity.ok(boards);
    }





}
