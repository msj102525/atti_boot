package org.ict.atti_boot.inquiry.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.inquiry.jpa.entity.InquiryEntity;
import org.ict.atti_boot.inquiry.model.inquiryService.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/inquiry")
@CrossOrigin
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    // 문의사항 리스트 가져오기
    @GetMapping("/inquiryList")
    public ResponseEntity<Map<String, Object>> getInquiryList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = inquiryService.getInquiries(page, size);
        return ResponseEntity.ok(response);
    }

    // 검색
    @GetMapping("/search")
    public ResponseEntity<List<InquiryEntity>> searchInquiries(
            @RequestParam Map<String, String> params) {
        List<InquiryEntity> inquiries = inquiryService.searchInquiries(params);
        return ResponseEntity.ok(inquiries);
    }

    // 문의사항 상세 정보 가져오기
    @GetMapping("/inquiryDetail/{inquiryNo}")
    public ResponseEntity<InquiryEntity> getInquiryDetail(@PathVariable int inquiryNo) {
        InquiryEntity inquiry = inquiryService.getInquiryDetail(inquiryNo);
        return ResponseEntity.ok(inquiry);
    }

    // 문의사항 수정
    @PutMapping("/inquiryUpdate/{inquiryNo}")
    public ResponseEntity<Void> updateInquiry(
            @PathVariable int inquiryNo,
            @RequestBody InquiryEntity inquiryData) {
        inquiryService.updateInquiry(inquiryNo, inquiryData);
        return ResponseEntity.noContent().build();
    }

    // 문의사항 등록
    @PostMapping("/create")
    public ResponseEntity<InquiryEntity> createInquiry(@RequestBody InquiryEntity inquiryData) {
        InquiryEntity createdInquiry = inquiryService.createInquiry(inquiryData);
        log.info("Created Inquiry: {}", createdInquiry);
        return ResponseEntity.ok(createdInquiry);
    }

    // 문의사항 삭제
    @DeleteMapping("/inquiryDelete/{inquiryNo}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable int inquiryNo) {
        inquiryService.deleteInquiry(inquiryNo);
        return ResponseEntity.noContent().build();
    }
}
