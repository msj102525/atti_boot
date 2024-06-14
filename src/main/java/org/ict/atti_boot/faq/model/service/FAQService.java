package org.ict.atti_boot.faq.model.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.faq.jpa.entity.FAQEntity;
import org.ict.atti_boot.faq.jpa.repository.FAQRepository;
import org.ict.atti_boot.faq.model.dto.FAQDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FAQService {

    @Autowired
    private FAQRepository faqRepository;

    public List<FAQDto> selectList() {

        List<FAQDto> answerList = faqRepository.findAll().stream()
                .map(FAQEntity::toDto)
                .collect(Collectors.toList());

        log.info("fqwegq" + answerList.size());

        return faqRepository.findAll().stream()
                .map(FAQEntity::toDto)
                .collect(Collectors.toList());
    }
}
