package org.ict.atti_boot.user.controller;

import org.ict.atti_boot.user.jpa.entity.File;
import org.ict.atti_boot.user.model.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            File savedFile = fileService.saveFile(file, userId);
            return ResponseEntity.ok(savedFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }
        // 파일 업데이트 로직 추가
        return ResponseEntity.ok("File updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("userId") String userId) {
        // 파일 삭제 로직 추가
        return ResponseEntity.ok("File deleted successfully");
    }
}