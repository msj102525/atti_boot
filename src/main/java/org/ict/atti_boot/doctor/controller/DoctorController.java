package org.ict.atti_boot.doctor.controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.model.dto.DoctorDto;
import org.ict.atti_boot.doctor.model.service.DoctorService;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    @GetMapping
    public ResponseEntity<List<DoctorDto>> getDoctors() {
        return ResponseEntity.ok(doctorService.findAllDoctor());
    }

    /*@PostMapping("")
    public ResponseEntity<?> boardWriting(HttpServletRequest request, @RequestBody Notice_Input noticeInput) {
        log.info("notice_input = {}", noticeInput);
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        String userEmail = jwtUtil.getUserEmailFromToken(token);
        boolean isAdmin = jwtUtil.isAdminFromToken(token);

        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. User is not an administrator.");
        }

        Optional<User> loginUser = userRepository.findByEmail(userEmail);
        if (loginUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        NoticeBoard noticeBoard = NoticeBoard.builder()
                .id(UUID.randomUUID())
                .authorId(loginUser.get().getId())
                .title(noticeInput.getTitle())
                .content(noticeInput.getContent())
                .isPinned(noticeInput.getIsPinned())
                .category("notice")
                .status("activated")
                .build();

        List<NoticeFile> noticeFiles = null;
        if (noticeInput.getFileIds() != null && !noticeInput.getFileIds().isEmpty()) {
            noticeFiles = noticeInput.getFileIds().stream()
                    .map(fileId -> {
                        NoticeFile noticeFile = new NoticeFile();
                        noticeFile.setId(UUID.randomUUID().toString());
                        noticeFile.setNoticeId(noticeBoard.getId().toString());
                        noticeFile.setFileId(fileId);
                        return noticeFile;
                    }).collect(Collectors.toList());
        }

        log.info("Notice Files to be saved: {}", noticeFiles);

        NoticeBoard savedNoticeBoard = noticeService.save(noticeBoard, noticeFiles);

        return new ResponseEntity<>(savedNoticeBoard, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getNoticesWithPinnedSeparate(
            @RequestParam(defaultValue = "notice") String category,
            @RequestParam(defaultValue = "inactivate") String status,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> notices = noticeService.getNoticesWithPinnedSeparate(category, status, title, page, size);
        return ResponseEntity.ok(notices);
    }

    @PostMapping("/read/{postId}")
    public ResponseEntity<String> incrementReadCount(@PathVariable String postId) {
        try {
            noticeService.incrementReadCount(postId);
            return ResponseEntity.ok("Read count incremented successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error incrementing read count: " + e.getMessage());
        }
    }

    @PostMapping("/likes")
    public ResponseEntity<String> toggleLike(HttpServletRequest request, @RequestBody UserNoticeLikeId requestData) {
        try {
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            Optional<User> userOptional = userRepository.findByEmail(jwtUtil.getUserEmailFromToken(token));
            if (userOptional.isPresent()) {
                requestData.setUserId(userOptional.get().getId());
                String result = noticeService.toggleLike(requestData);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing like toggle: " + e.getMessage());
        }
    }*/
}
