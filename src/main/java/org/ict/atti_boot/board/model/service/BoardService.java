package org.ict.atti_boot.board.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.ict.atti_boot.board.jpa.repository.BoardRepository;
import org.ict.atti_boot.board.model.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Page<BoardDto> selectList(PageRequest pageRequest) {
        log.info("Fetching board list");
        Page<BoardEntity> pages = boardRepository.findAllOrdered(pageRequest);
        return pages.map(BoardEntity::toDto); // Page<BoardEntity>를 Page<BoardDto>로 변환
    }

    public BoardDto getBoardDetail(int boardNum) {
        Optional<BoardEntity> boardEntity = boardRepository.findById(boardNum);
        if (boardEntity.isPresent()) {
            return boardEntity.get().toDto();
        } else {
            throw new IllegalArgumentException("Invalid boardNum: " + boardNum);
        }
    }
}
