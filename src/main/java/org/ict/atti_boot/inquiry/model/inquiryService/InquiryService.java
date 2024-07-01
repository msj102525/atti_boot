package org.ict.atti_boot.inquiry.model.inquiryService;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.inquiry.jpa.entity.InquiryEntity;
import org.ict.atti_boot.inquiry.jpa.repository.InquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@Service
@Transactional
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Transactional
    public Map<String, Object> getInquiries(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        List<InquiryEntity> inquiries = inquiryRepository.findAll(paging).getContent();
        long totalItems = inquiryRepository.count();

        Map<String, Object> response = new HashMap<>();
        response.put("content", inquiries);
        response.put("currentPage", page);
        response.put("totalItems", totalItems);
        response.put("totalPages", (int) Math.ceil((double) totalItems / size));

        return response;
    }

    @Transactional
    public List<InquiryEntity> searchInquiries(Map<String, String> params) {
        //제목을 검색
        return inquiryRepository.findByTitleContaining(params.get("keyword"));
    }

    @Transactional
    public InquiryEntity getInquiryDetail(int inquiryNo) {
        return inquiryRepository.findById(inquiryNo)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
    }

    @Transactional
    public void updateInquiry(int inquiryNo, InquiryEntity inquiryData) {
        InquiryEntity inquiry = getInquiryDetail(inquiryNo);
        inquiry.setTitle(inquiryData.getTitle());
        inquiry.setContent(inquiryData.getContent());
        inquiryRepository.save(inquiry);
    }

    @Transactional
    public InquiryEntity createInquiry(InquiryEntity inquiryData) {
        return inquiryRepository.save(inquiryData);
    }

//    @Transactional
//    public void deleteInquiry(int inquiryNo) {
//        inquiryRepository.deleteById(inquiryNo);
//    }
    @Transactional
    public void deleteInquiry(int inquiryNo) {
        inquiryRepository.deleteById(inquiryNo);
        adjustInquiryNumbersAfterDeletion(inquiryNo);
    }
    private void adjustInquiryNumbersAfterDeletion(int deletedInquiryNo) {
        List<InquiryEntity> inquiries = inquiryRepository.findByInquiryNoGreaterThan(deletedInquiryNo);
        for (InquiryEntity inquiry : inquiries) {
            inquiry.setInquiryNo(inquiry.getInquiryNo() - 1);
            inquiryRepository.save(inquiry);
        }
    }


    @Transactional
    public void deleteInquiriesByUserId(String userId) {
    inquiryRepository.deleteByUserId(userId);
    }



}
