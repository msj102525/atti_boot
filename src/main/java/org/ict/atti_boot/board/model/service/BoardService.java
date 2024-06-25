package org.ict.atti_boot.board.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.ict.atti_boot.board.jpa.repository.BoardRepository;
import org.ict.atti_boot.board.model.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(boardNum);
        if (boardEntityOptional.isPresent()) {
            BoardEntity boardEntity = boardEntityOptional.get();
            // 조회수 증가
            boardEntity.setReadCount(boardEntity.getReadCount() + 1);
            boardRepository.save(boardEntity); // 변경사항 저장

            return boardEntity.toDto();
        } else {
            throw new IllegalArgumentException("Invalid boardNum: " + boardNum);
        }
    }

    public void updateBoard(int boardNum, BoardDto boardDto) {
        BoardEntity existingBoard = boardRepository.findById(boardNum)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board number: " + boardNum));

        existingBoard.setBoardTitle(boardDto.getBoardTitle());
        existingBoard.setBoardContent(boardDto.getBoardContent());
        existingBoard.setImportance(boardDto.getImportance());

        boardRepository.save(existingBoard);
    }


    public void deleteBoard(int boardNum) {
        boardRepository.deleteById(boardNum);
    }

    // 검색
    public Page<BoardEntity> getBoards(String action, String keyword, LocalDateTime beginDate, LocalDateTime endDate, Pageable pageable) {
        if ("title".equals(action)) {
            return boardRepository.findAllByBoardTitle(keyword, pageable);
        } else if ("writer".equals(action)) {
            return boardRepository.findAllByBoardWriter(keyword, pageable);
        } else if ("date".equals(action)) {
            return boardRepository.findAllByBoardDateBetween(beginDate, endDate, pageable);
        } else if (action == null){
            Page<BoardEntity> pages = boardRepository.findAllOrdered(pageable);
            return pages; //
        }

        else {
            throw new IllegalArgumentException("Invalid search action: " + action);
        }
    }

    @Transactional
    public void insertBoard(BoardDto boardDto) {
        log.info("디티오" + boardDto + "wegwe");
        BoardEntity boardEntity = boardDto.toEntity();
        log.info(boardEntity + "wegwe");
        boardRepository.save(boardEntity);
    }


}
