package org.ict.atti_boot.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.board.model.dto.BoardDto;
import org.ict.atti_boot.board.model.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@CrossOrigin

public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/boardList")
    public ResponseEntity<Page<BoardDto>> getBoardList(Pageable pageable) {
        log.info("테스트1");
        Page<BoardDto> boardList = boardService.selectList(pageable);
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }
}
