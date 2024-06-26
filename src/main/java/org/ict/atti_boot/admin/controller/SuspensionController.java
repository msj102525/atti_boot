package org.ict.atti_boot.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.model.dto.SuspensionDto;
import org.ict.atti_boot.admin.model.entity.SuspensionEntity;
import org.ict.atti_boot.admin.service.SuspensionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Slf4j
public class SuspensionController {
    private final SuspensionService suspensionService;

    public SuspensionController(SuspensionService suspensionService) {
        this.suspensionService = suspensionService;
    }

    @PostMapping("/api/suspendmembers")
    public ResponseEntity<SuspensionEntity> suspendMember(@RequestBody SuspensionEntity suspension) {
        log.info("Received suspension request: {}", suspension);
        suspension.setSuspensionStatus("unactive"); // 상태를 직접 설정
        SuspensionEntity createdSuspension = suspensionService.suspendMember(suspension);
        return ResponseEntity.ok(createdSuspension);
    }


    @GetMapping("/suspensionMemberList")
    public ResponseEntity<Map<String, Object>> getAllSuspensionMembers(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "searchInput", required = false) String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            Map<String, Object> response = suspensionService.getAllSuspensionMembers(page, size, searchField, searchInput);
            if (((List<?>) response.get("members")).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching members: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/deletesuspensionmembers/{suspensionNo}")
    public ResponseEntity<?> deleteSuspensionMember(@PathVariable Long suspensionNo) {
        // 여기에 회원 삭제 로직을 추가합니다.
        try {
            // userId에 해당하는 회원을 삭제하고 성공 응답을 반환합니다.
            suspensionService.deleteSuspensionMember(suspensionNo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 삭제 중 오류가 발생하면 500 Internal Server Error 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
