package org.ict.atti_boot.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/profile")
@CrossOrigin
public class FileUpdown {

    private final UserService userService;

    public FileUpdown(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/upload")
    public ResponseEntity<?> uploadProfilePhoto(@RequestPart(name="userId", required = false) String userId,@RequestPart("file") MultipartFile file) {
        try {
            User updatedUser = userService.uploadProfilePhoto(userId, file);
            log.info("file업로드111",file.toString());
            return ResponseEntity.ok().body(updatedUser.getProfileUrl());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("파일 업로드 실패: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProfilePhoto(@PathVariable("userId") String userId) {
        try {
            userService.deleteProfilePhoto(userId);
            return ResponseEntity.ok().body("프로필 사진 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("프로필 사진 삭제 실패: " + e.getMessage());
        }
    }

}