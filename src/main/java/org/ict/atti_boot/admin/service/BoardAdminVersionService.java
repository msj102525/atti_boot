package org.ict.atti_boot.admin.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.model.dto.*;
import org.ict.atti_boot.admin.model.entity.*;
import org.ict.atti_boot.admin.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BoardAdminVersionService {
    private final CommunityAdminVersionRepository communityAdminVersionRepository;
    private final NoticeAdminVersionRepository noticeAdminVersionRepository;
    private final FaqAdminVersionRepository faqAdminVersionRepository;
    private final InquiryAdminVersionRepository inquiryAdminVersionRepository;
    private final OnewordAdminVersionRepository onewordAdminVersionRepository;

    public BoardAdminVersionService(CommunityAdminVersionRepository communityAdminVersionRepository, NoticeAdminVersionRepository noticeAdminVersionRepository, FaqAdminVersionRepository faqAdminVersionRepository, InquiryAdminVersionRepository inquiryAdminVersionRepository, OnewordAdminVersionRepository onewordAdminVersionRepository) {
        this.communityAdminVersionRepository = communityAdminVersionRepository;
        this.noticeAdminVersionRepository = noticeAdminVersionRepository;
        this.faqAdminVersionRepository = faqAdminVersionRepository;
        this.inquiryAdminVersionRepository = inquiryAdminVersionRepository;
        this.onewordAdminVersionRepository = onewordAdminVersionRepository;
    }

    // 커뮤니티(admin ver.) ***********************************

    public List<CommunityAdminVersionDto> getAllCommunityAdminVersion(int page, int size, String searchField, String searchInput) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityAdminVersionEntity> memberPage;

        log.info("Page: {}, Size: {}, Search Field: {}, Search Input: {}", page, size, searchField, searchInput);

        // 검색 필드에 따라 적절한 메서드 호출
        if ("userId".equals(searchField)) {
            memberPage = communityAdminVersionRepository.findByUserId(searchInput, pageable);
        } else if ("feedContent".equals(searchField)) {
            memberPage = communityAdminVersionRepository.findByFeedContent(searchInput, pageable);
        } else {
            // 검색 조건이 없는 경우 전체 회원 조회
            memberPage = communityAdminVersionRepository.findAll(pageable);
        }

        log.info("Total Elements: {}", memberPage.getTotalElements());

        return memberPage.getContent().stream()
                .map(this::convertToCommunityAdminVersionDto)
                .collect(Collectors.toList());
    }

    // AdminEntity를 AdminDto로 변환하는 메서드
    private CommunityAdminVersionDto convertToCommunityAdminVersionDto(CommunityAdminVersionEntity communityAdminVersionEntity) {

        // 나머지 필드도 설정
        return new CommunityAdminVersionDto(communityAdminVersionEntity.getUserId(),
                communityAdminVersionEntity.getFeedNum(),
                communityAdminVersionEntity.getFeedContent());

    }

    // 커뮤니티보드(admin ver.) 삭제 메서드
    public void deleteCommunityAdminVersion(Long feedNum) {
        // userId에 해당하는 회원을 삭제합니다.
        communityAdminVersionRepository.deleteById(feedNum);
    }


    // *******************************************

    // 공지사항(admin ver.) ***************************************


    public List<NoticeAdminVersionDto> getAllNoticeAdminVersion(int page, int size, String searchField, String searchInput) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NoticeAdminVersionEntity> memberPage;

        log.info("Page: {}, Size: {}, Search Field: {}, Search Input: {}", page, size, searchField, searchInput);

        // 검색 필드에 따라 적절한 메서드 호출
        if ("boardWriter".equals(searchField)) {
            memberPage = noticeAdminVersionRepository.findByBoardWriter(searchInput, pageable);
        } else if ("boardContent".equals(searchField)) {
            memberPage = noticeAdminVersionRepository.findByBoardContent(searchInput, pageable);
        } else {
            // 검색 조건이 없는 경우 전체 회원 조회
            memberPage = noticeAdminVersionRepository.findAll(pageable);
        }

        log.info("Total Elements: {}", memberPage.getTotalElements());

        return memberPage.getContent().stream()
                .map(this::convertToNoticeAdminVersionDto)
                .collect(Collectors.toList());
    }

    // AdminEntity를 AdminDto로 변환하는 메서드
    private NoticeAdminVersionDto convertToNoticeAdminVersionDto(NoticeAdminVersionEntity noticeAdminVersionEntity) {

        // 나머지 필드도 설정
        return new NoticeAdminVersionDto(noticeAdminVersionEntity.getBoardNum(),
                noticeAdminVersionEntity.getBoardTitle(),
                noticeAdminVersionEntity.getBoardWriter(),
                noticeAdminVersionEntity.getBoardContent());

    }

    // 공지사항보드(admin ver.) 삭제 메서드
    public void deleteNoticeAdminVersion(Long boardNum) {
        // userId에 해당하는 회원을 삭제합니다.
        noticeAdminVersionRepository.deleteById(boardNum);
    }






    // *************************************************************


    // FAQ(admin ver.) **********************************************

    public List<FaqAdminVersionDto> getAllFaqAdminVersion(int page, int size, String searchField, String searchInput) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FaqAdminVersionEntity> memberPage;

        log.info("Page: {}, Size: {}, Search Field: {}, Search Input: {}", page, size, searchField, searchInput);

        // 검색 필드에 따라 적절한 메서드 호출
        if ("faqTitle".equals(searchField)) {
            memberPage = faqAdminVersionRepository.findByFaqTitle(searchInput, pageable);
        } else if ("faqContent".equals(searchField)) {
            memberPage = faqAdminVersionRepository.findByFaqContent(searchInput, pageable);
        } else {
            // 검색 조건이 없는 경우 전체 회원 조회
            memberPage = faqAdminVersionRepository.findAll(pageable);
        }

        log.info("Total Elements: {}", memberPage.getTotalElements());

        return memberPage.getContent().stream()
                .map(this::convertToFaqAdminVersionDto)
                .collect(Collectors.toList());
    }

    // AdminEntity를 AdminDto로 변환하는 메서드
    private FaqAdminVersionDto convertToFaqAdminVersionDto(FaqAdminVersionEntity faqAdminVersionEntity) {

        // 나머지 필드도 설정
        return new FaqAdminVersionDto(faqAdminVersionEntity.getFaqNum(),
                faqAdminVersionEntity.getFaqTitle(),
                faqAdminVersionEntity.getFaqWriter(),
                faqAdminVersionEntity.getFaqContent());

    }

    // 공지사항보드(admin ver.) 삭제 메서드
    public void deleteFaqAdminVersion(Long faqNum) {
        // userId에 해당하는 회원을 삭제합니다.
        faqAdminVersionRepository.deleteById(faqNum);
    }


    // **************************************************************


    // 문의하기(admin ver.) *****************************************

    public List<InquiryAdminVersionDto> getAllInquiryAdminVersion(int page, int size, String searchField, String searchInput) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryAdminVersionEntity> memberPage;

        log.info("Page: {}, Size: {}, Search Field: {}, Search Input: {}", page, size, searchField, searchInput);

        // 검색 필드에 따라 적절한 메서드 호출
        if ("userId".equals(searchField)) {
            memberPage = inquiryAdminVersionRepository.findByUserId(searchInput, pageable);
        } else if ("content".equals(searchField)) {
            memberPage = inquiryAdminVersionRepository.findByContent(searchInput, pageable);
        } else {
            // 검색 조건이 없는 경우 전체 회원 조회
            memberPage = inquiryAdminVersionRepository.findAll(pageable);
        }

        log.info("Total Elements: {}", memberPage.getTotalElements());

        return memberPage.getContent().stream()
                .map(this::convertToInquiryAdminVersionDto)
                .collect(Collectors.toList());
    }

    // AdminEntity를 AdminDto로 변환하는 메서드
    private InquiryAdminVersionDto convertToInquiryAdminVersionDto(InquiryAdminVersionEntity inquiryAdminVersionEntity) {

        // 나머지 필드도 설정
        return new InquiryAdminVersionDto(inquiryAdminVersionEntity.getInquiryNo(),
                inquiryAdminVersionEntity.getUserId(),
                inquiryAdminVersionEntity.getTitle(),
                inquiryAdminVersionEntity.getContent());

    }

    // 공지사항보드(admin ver.) 삭제 메서드
    public void deleteInquiryAdminVersion(Long inquiryNo) {
        // userId에 해당하는 회원을 삭제합니다.
        inquiryAdminVersionRepository.deleteById(inquiryNo);
    }




    // ************************************************************


    // 오늘 한 줄 (admin ver.) **************************************

    public List<OnewordAdminVersionDto> getAllOnewordAdminVersion(int page, int size, String searchField, String searchInput) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OnewordAdminVersionEntity> memberPage;

        log.info("Page: {}, Size: {}, Search Field: {}, Search Input: {}", page, size, searchField, searchInput);

        // 검색 필드에 따라 적절한 메서드 호출
        if ("owsjWriter".equals(searchField)) {
            memberPage = onewordAdminVersionRepository.findByOwsjWriter(searchInput, pageable);
        } else if ("owsjSubject".equals(searchField)) {
            memberPage = onewordAdminVersionRepository.findByOwsjSubject(searchInput, pageable);
        } else {
            // 검색 조건이 없는 경우 전체 회원 조회
            memberPage = onewordAdminVersionRepository.findAll(pageable);
        }

        log.info("Total Elements: {}", memberPage.getTotalElements());

        return memberPage.getContent().stream()
                .map(this::convertToOnewordAdminVersionDto)
                .collect(Collectors.toList());
    }

    // AdminEntity를 AdminDto로 변환하는 메서드
    private OnewordAdminVersionDto convertToOnewordAdminVersionDto(OnewordAdminVersionEntity onewordAdminVersionEntity) {

        // 나머지 필드도 설정
        return new OnewordAdminVersionDto(onewordAdminVersionEntity.getOwsjNum(),
                onewordAdminVersionEntity.getOwsjSubject(),
                onewordAdminVersionEntity.getOwsjWriter());


    }

    // 공지사항보드(admin ver.) 삭제 메서드
    public void deleteOnewordAdminVersion(Long owsjNum) {
        // userId에 해당하는 회원을 삭제합니다.
        onewordAdminVersionRepository.deleteById(owsjNum);
    }



    // **************************************************************




}
