package org.ict.atti_boot.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.model.dto.*;
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

    // FAQ(admin ver.) **********************************************

    @GetMapping("/faqAdminVersionList")
    public ResponseEntity<List<FaqAdminVersionDto>> getAllFaqAdminVersion(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "searchInput", required = false) String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            List<FaqAdminVersionDto> members = boardAdminVersionService.getAllFaqAdminVersion(page, size, searchField, searchInput);
            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching members: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/deletefaqAdminVersion/{faqNum}")
    public ResponseEntity<?> deleteFaqAdminVersion(@PathVariable Long faqNum) {
        // 여기에 회원 삭제 로직을 추가합니다.
        try {
            // userId에 해당하는 회원을 삭제하고 성공 응답을 반환합니다.
            boardAdminVersionService.deleteFaqAdminVersion(faqNum);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 삭제 중 오류가 발생하면 500 Internal Server Error 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }






    // ************************************************************


    // 문의하기(admin ver.) ****************************************

    @GetMapping("/inquiryAdminVersionList")
    public ResponseEntity<List<InquiryAdminVersionDto>> getAllInquiryAdminVersion(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "searchInput", required = false) String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            List<InquiryAdminVersionDto> members = boardAdminVersionService.getAllInquiryAdminVersion(page, size, searchField, searchInput);
            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching members: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/deleteinquiryAdminVersion/{inquiryNo}")
    public ResponseEntity<?> deleteInquiryAdminVersion(@PathVariable Long inquiryNo) {
        // 여기에 회원 삭제 로직을 추가합니다.
        try {
            // userId에 해당하는 회원을 삭제하고 성공 응답을 반환합니다.
            boardAdminVersionService.deleteInquiryAdminVersion(inquiryNo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 삭제 중 오류가 발생하면 500 Internal Server Error 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    // *******************************************************


    //오늘 한 줄 (admin ver.) **************************************

    @GetMapping("/onewordAdminVersionList")
    public ResponseEntity<List<OnewordAdminVersionDto>> getAllOnewordAdminVersion(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "searchInput", required = false) String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            List<OnewordAdminVersionDto> members = boardAdminVersionService.getAllOnewordAdminVersion(page, size, searchField, searchInput);
            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching members: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/deleteonewordAdminVersion/{owsjNum}")
    public ResponseEntity<?> deleteOnewordAdminVersion(@PathVariable Long owsjNum) {
        // 여기에 회원 삭제 로직을 추가합니다.
        try {
            // userId에 해당하는 회원을 삭제하고 성공 응답을 반환합니다.
            boardAdminVersionService.deleteOnewordAdminVersion(owsjNum);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 삭제 중 오류가 발생하면 500 Internal Server Error 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // ************************************************************

}
