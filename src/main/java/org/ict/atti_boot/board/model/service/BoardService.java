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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<BoardDto> searchBoards(String action, String keyword, String beginDate, String endDate) {
    List<BoardEntity> boardEntities;

    switch (action) {
        case "title":
            boardEntities = boardRepository.findByBoardTitleContaining(keyword);
            break;
        case "writer":
            boardEntities = boardRepository.findByBoardWriterContaining(keyword);
            break;
        case "date":
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = dateFormat.parse(beginDate);
                Date eDate = dateFormat.parse(endDate);
                boardEntities = boardRepository.findByBoardDateBetween(startDate, eDate);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format: " + e.getMessage());
            }
            break;
        default:
            throw new IllegalArgumentException("Invalid search action: " + action);
    }

    return boardEntities.stream().map(BoardEntity::toDto).collect(Collectors.toList());
}

    @Transactional
    public void insertBoard(BoardDto boardDto) {
        log.info("디티오" + boardDto + "wegwe");
        BoardEntity boardEntity = boardDto.toEntity();
        log.info(boardEntity + "wegwe");
        boardRepository.save(boardEntity);
    }


}
