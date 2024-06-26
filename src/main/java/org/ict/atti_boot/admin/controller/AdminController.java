package org.ict.atti_boot.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.model.dto.AdminDto;
import org.ict.atti_boot.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

//    @GetMapping("/memberList")
//    public List<AdminDto> getAllMembers() {
//        return adminService.getAllMembers();
//
//
//    }

//    @GetMapping("/memberList")
//    public ResponseEntity<List<AdminDto>> getAllMembers(
//
//    ) {
//        try {
//            List<AdminDto> members = adminService.getAllMembers();
//            if (members.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(members, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Error while fetching members: {}", e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/memberList")
    public ResponseEntity<Map<String, Object>> getAllMembers(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "searchInput", required = false) String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
//            List<AdminDto> members = adminService.getAllMembers(page, size, searchField, searchInput);
            Map<String, Object> response = adminService.getAllMembers(page, size, searchField, searchInput);
            if (((List<?>) response.get("members")).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching members: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/memberList")
//    public ResponseEntity<List<AdminDto>> getAllMembers(
//            @RequestParam(value = "searchField", required = false) String searchField,
//            @RequestParam(value = "searchInput", required = false) String searchInput,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size) {
//        try {
//            List<AdminDto> members = adminService.getAllMembers(page, size, searchField, searchInput);
//            if (members.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(members, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @DeleteMapping("/api/deletemembers/{userId}")
    public ResponseEntity<?> deleteMember(@PathVariable String userId) {
        // 여기에 회원 삭제 로직을 추가합니다.
        try {
            // userId에 해당하는 회원을 삭제하고 성공 응답을 반환합니다.
            adminService.deleteMember(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 삭제 중 오류가 발생하면 500 Internal Server Error 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PutMapping("/api/updatemembers/{userId}")
//    public ResponseEntity<String> updateMember(@PathVariable String userId, @RequestBody AdminDto adminDto) {
//        try {
//            // 클라이언트로부터 받은 회원 정보를 서비스로 전달하여 업데이트 수행
//            adminService.updateMember(userId, adminDto);
//            return ResponseEntity.ok().body("Member updated successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update member.");
//        }
//    }

    @PutMapping("/api/updatemembers/{userId}")
    public ResponseEntity<String> updateMember(@PathVariable String userId, @RequestBody AdminDto adminDto) {
        // 디버깅을 위해 adminDto를 콘솔에 출력
        System.out.println("Received update request for userId: " + userId + " with data: " + adminDto);

        if (adminDto.getUserName() == null || adminDto.getUserName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name cannot be null or empty");
        }

        try {
            adminService.updateMember(userId, adminDto);
            return ResponseEntity.ok().body("Member updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update member.");
        }
    }





}