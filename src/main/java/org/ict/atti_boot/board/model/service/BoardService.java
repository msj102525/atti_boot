package org.ict.atti_boot.board.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.ict.atti_boot.board.jpa.repository.BoardRepository;
import org.ict.atti_boot.board.model.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Page<BoardDto> selectList(Pageable pageable) {
        log.info("테스트2");
        Page<BoardEntity> pages = boardRepository.findAll(pageable);
        return pages.map(BoardEntity::toDto); // Page<BoardEntity>를 Page<BoardDto>로 변환
    }
}
