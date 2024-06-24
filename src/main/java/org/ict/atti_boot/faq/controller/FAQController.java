package org.ict.atti_boot.faq.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.faq.model.dto.FAQDto;
import org.ict.atti_boot.faq.model.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
@CrossOrigin
public class FAQController {

    @Autowired
    private FAQService faqService;

    // 전체 리스트 출력
    @GetMapping("/faq")
    public Page<FAQDto> getAllFAQs(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return faqService.getAllFAQs(pageable);
    }

    @GetMapping("/faq/{faqNum}")
    public ResponseEntity<FAQDto> getFAQById(@PathVariable int faqNum) {
        Optional<FAQDto> faq = faqService.getFAQById(faqNum);
        return faq.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/faq")
    public ResponseEntity<FAQDto> createFAQ(@RequestBody FAQDto faqDto) {
        log.info("createFAQ: {}", faqDto);
        FAQDto createdFAQ = faqService.createFAQ(faqDto);
        return ResponseEntity.ok(createdFAQ);
    }

    @PutMapping("/faq/{faqNum}")
    public ResponseEntity<FAQDto> updateFAQ(@PathVariable int faqNum, @RequestBody FAQDto faqDto) {
        Optional<FAQDto> updatedFAQ = faqService.updateFAQ(faqNum, faqDto);
        return updatedFAQ.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{faqNum}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable int faqNum) {
        if (faqService.deleteFAQ(faqNum)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
