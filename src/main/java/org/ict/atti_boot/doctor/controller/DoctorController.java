package org.ict.atti_boot.doctor.controller;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.model.dto.EmailRequest;
import org.ict.atti_boot.doctor.model.service.DoctorService;
import org.ict.atti_boot.review.model.ReviewService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;
    private final ReviewService reviewService;

    public DoctorController(DoctorService doctorService, ReviewService reviewService) {
        this.doctorService = doctorService;
        this.reviewService = reviewService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getDoctors(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info(pageable.toString());
        return ResponseEntity.ok(doctorService.findAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDoctorDetail(@PageableDefault(sort = "writeDate", direction = Sort.Direction.ASC) Pageable pageable,@PathVariable(name="id",required = false) String doctorId) {
        Map<String, Object> response = new HashMap<>();
        response.put("doctor", doctorService.getDoctorById(doctorId));
        response.put("reviews", reviewService.findAllByDoctorId(doctorId, pageable));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDoctors(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(name="selectedTags",required = false) List<String> tags,
            @RequestParam(name="keyword", required = false) String name,
            @RequestParam(name="gender", required = false) Character gender) {
        long tagCount = tags.size();
        return ResponseEntity.ok(doctorService.findByAllConditions(name, tags, tagCount, gender,pageable));
    }

    @PostMapping("/mail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            request.setNumberOfMembers(doctorService.findAllUserCount());
            doctorService.sendEmail(request);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
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
