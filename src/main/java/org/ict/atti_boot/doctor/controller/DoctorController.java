package org.ict.atti_boot.doctor.controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.model.dto.DoctorDto;
import org.ict.atti_boot.doctor.model.service.DoctorService;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getDoctors(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(doctorService.findAll(pageable));
    }
//    @GetMapping("/search")
//    public ResponseEntity<Map<String, Object>> searchDoctorsByName(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam("keyword") String name) {
//        return ResponseEntity.ok(doctorService.findByName(name, pageable));
//    }
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDoctors(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(name="selectedTags",required = false) List<String> tags,
            @RequestParam(name="keyword", required = false) String name,
            @RequestParam(name="gender", required = false) Character gender) {
//        if(name.length()>0 && gender.length()>0 && tags.size()>0) {
//            return ResponseEntity.ok(doctorService.searchByAll(pageable, name, gender, tags));
//        }
//        if(gender.length()>0 && tags.size()>0) {
//            return ResponseEntity.ok(doctorService.searchByTagsAndGender(pageable, gender, tags));
//        }
//        if(name.length()>0 && tags.size()>0) {
//            return ResponseEntity.ok(doctorService.searchByTagsAndName(pageable, name, tags));
//        }
//        if(name.length()>0 && gender.length()>0) {
//            return ResponseEntity.ok(doctorService.searchByGenderAndName(pageable, name, gender));
//        }
//       if(name.length()>0){
//            return ResponseEntity.ok(doctorService.findByName(name, pageable));
//       }
//       if(gender.length()>0){
//           Character genderc = gender.charAt(0);
//           return ResponseEntity.ok(doctorService.findByGender(genderc, pageable));
//        }else {
//            return ResponseEntity.ok(doctorService.findByTags(tags,pageable));
//        }
        long tagCount = tags.size();
        return ResponseEntity.ok(doctorService.findByAllConditions(name, tags, tagCount, gender,pageable));
    }


    @GetMapping("/test")
    public ResponseEntity<List<DoctorDto>> getDoctor() {
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
