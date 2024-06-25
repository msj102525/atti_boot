package org.ict.atti_boot.board.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.ict.atti_boot.board.jpa.repository.BoardRepository;
import org.ict.atti_boot.board.model.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    private final String uploadDir = "uploads/";

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

    public BoardDto updateBoard(int boardNum, BoardDto boardDto, MultipartFile file) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardNum);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            boardEntity.setBoardTitle(boardDto.getBoardTitle());
            boardEntity.setBoardContent(boardDto.getBoardContent());
            boardEntity.setImportance(boardDto.getImportance());

            if (file != null && !file.isEmpty()) {
                try {
                    String fileName = file.getOriginalFilename();
                    Path uploadPath = Paths.get(uploadDir);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    Path filePathObj = uploadPath.resolve(fileName);
                    file.transferTo(filePathObj.toFile());
                    boardEntity.setFilePath(filePathObj.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            BoardEntity updatedEntity = boardRepository.save(boardEntity);
            return updatedEntity.toDto();
        } else {
            return null;
        }
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

    // 등록 처리
    @Transactional
    public BoardDto createBoard(BoardDto boardDto, MultipartFile file) {
        String filePath = null;
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePathObj = uploadPath.resolve(fileName);
                file.transferTo(filePathObj.toFile());

                filePath = filePathObj.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BoardEntity boardEntity = boardDto.toEntity();
        boardEntity.setFilePath(filePath);
        BoardEntity savedEntity = boardRepository.save(boardEntity);
        return savedEntity.toDto();
    }


    // 파일 다운 처리
    public ResponseEntity<Resource> downloadFile(String filename) {
        try {
            File file = new File(uploadDir + filename);
            if (file.exists()) {
                Resource resource = new FileSystemResource(file);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

                String mimeType = Files.probeContentType(file.toPath());
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }


}
