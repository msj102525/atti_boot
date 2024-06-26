package org.ict.atti_boot.faq.model.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.faq.jpa.entity.FAQEntity;
import org.ict.atti_boot.faq.jpa.repository.FAQRepository;
import org.ict.atti_boot.faq.model.dto.FAQDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FAQService {

    @Autowired
    private FAQRepository faqRepository;

    public Page<FAQDto> getAllFAQs(Pageable pageable) {
        return faqRepository.findAll(pageable).map(FAQEntity::toDto);
    }

    public Optional<FAQDto> getFAQById(int faqNum) {
        return faqRepository.findById(faqNum).map(FAQEntity::toDto);
    }

    public FAQDto createFAQ(FAQDto faqDto) {
        FAQEntity faqEntity = faqDto.toEntity();
        FAQEntity savedEntity = faqRepository.save(faqEntity);
        return savedEntity.toDto();
    }

    public Optional<FAQDto> updateFAQ(int faqNum, FAQDto faqDto) {
        return faqRepository.findById(faqNum).map(existingFAQ -> {
            existingFAQ.setFaqTitle(faqDto.getFaqTitle());
            existingFAQ.setFaqContent(faqDto.getFaqContent());
            existingFAQ.setFaqCategory(faqDto.getFaqCategory());
            FAQEntity updatedEntity = faqRepository.save(existingFAQ);
            return updatedEntity.toDto();
        });
    }

    public boolean deleteFAQ(int faqNum) {
        if (faqRepository.existsById(faqNum)) {
            faqRepository.deleteById(faqNum);
            return true;
        } else {
            return false;
        }
    }



}
