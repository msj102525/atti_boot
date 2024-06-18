package org.ict.atti_boot.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.model.dto.CommunityAdminVersionDto;
import org.ict.atti_boot.admin.service.BoardAdminVersionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // **********************************************************



}
