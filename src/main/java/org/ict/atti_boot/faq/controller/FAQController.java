package org.ict.atti_boot.faq.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.faq.model.dto.FAQDto;
import org.ict.atti_boot.faq.model.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
@CrossOrigin
public class FAQController {

    @Autowired
    private FAQService faqService;

    // 전체 리스트 출력
    @GetMapping("/faqList")
    public List<FAQDto> getFAQList() {
        log.info("spqw");
        List<FAQDto> list = faqService.selectList();
        log.info("listqwef" + list);
        return faqService.selectList();
    }




}
