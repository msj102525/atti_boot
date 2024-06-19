package org.ict.atti_boot.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.model.dto.CommunityAdminVersionDto;
import org.ict.atti_boot.admin.model.dto.NoticeAdminVersionDto;
import org.ict.atti_boot.admin.service.BoardAdminVersionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class BoardAdminVersionController {

    private final BoardAdminVersionService boardAdminVersionService;

    public BoardAdminVersionController(BoardAdminVersionService boardAdminVersionService) {

        this.boardAdminVersionService = boardAdminVersionService;
    }

    // 커뮤니티(admin ver.) ***********************************

    @GetMapping("/communityAdminVersionList")
    public ResponseEntity<List<CommunityAdminVersionDto>> getAllCommunityAdminVersion(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "searchInput", required = false) String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            List<CommunityAdminVersionDto> members = boardAdminVersionService.getAllCommunityAdminVersion(page, size, searchField, searchInput);
            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching members: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/deletecommunityAdminVersion/{feedNum}")
    public ResponseEntity<?> deleteCommunityAdminVersion(@PathVariable Long feedNum) {
        // 여기에 회원 삭제 로직을 추가합니다.
        try {
            // userId에 해당하는 회원을 삭제하고 성공 응답을 반환합니다.
            boardAdminVersionService.deleteCommunityAdminVersion(feedNum);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 삭제 중 오류가 발생하면 500 Internal Server Error 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // **********************************************************


    // 공지사항(admin ver.) *************************************

    @GetMapping("/noticeAdminVersionList")
    public ResponseEntity<List<NoticeAdminVersionDto>> getAllNoticeAdminVersion(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "searchInput", required = false) String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            List<NoticeAdminVersionDto> members = boardAdminVersionService.getAllNoticeAdminVersion(page, size, searchField, searchInput);
            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching members: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/deletenoticeAdminVersion/{boardNum}")
    public ResponseEntity<?> deleteNoticeAdminVersion(@PathVariable Long boardNum) {
        // 여기에 회원 삭제 로직을 추가합니다.
        try {
            // userId에 해당하는 회원을 삭제하고 성공 응답을 반환합니다.
            boardAdminVersionService.deleteNoticeAdminVersion(boardNum);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 삭제 중 오류가 발생하면 500 Internal Server Error 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    // *********************************************************

}
