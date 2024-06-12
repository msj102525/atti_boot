package org.ict.atti_boot.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.board.model.dto.BoardDto;
import org.ict.atti_boot.board.model.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@CrossOrigin
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/boardList")
    public Page<BoardDto> getBoardList(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardService.selectList(pageRequest);
    }

    @GetMapping("/boardDetail/{boardNum}")
    public BoardDto getBoardDetail(@PathVariable int boardNum) {
        return boardService.getBoardDetail(boardNum);
    }
}
