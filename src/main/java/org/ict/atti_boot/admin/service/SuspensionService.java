package org.ict.atti_boot.admin.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.model.dto.AdminDto;
import org.ict.atti_boot.admin.model.dto.SuspensionDto;
import org.ict.atti_boot.admin.model.entity.SuspensionEntity;
import org.ict.atti_boot.admin.repository.SuspensionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class SuspensionService {

    private final SuspensionRepository suspensionRepository;

    public SuspensionService(SuspensionRepository suspensionRepository) {
        this.suspensionRepository = suspensionRepository;
    }

    public SuspensionEntity suspendMember(SuspensionEntity suspension) {
        Optional<SuspensionEntity> existingSuspension = suspensionRepository.findByUserIdAndSuspensionStatus(suspension.getUserId(), "unactive");
        if (existingSuspension.isPresent()) {
            throw new IllegalStateException("This user is already suspended.");
        }
        return suspensionRepository.save(suspension);
    }

    public Map<String, Object> getAllSuspensionMembers(int page, int size, String searchField, String searchInput) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SuspensionEntity> memberPage;

        log.info("Page: {}, Size: {}, Search Field: {}, Search Input: {}", page, size, searchField, searchInput);

        // 검색 필드에 따라 적절한 메서드 호출
        if ("userId".equals(searchField)) {
            memberPage = suspensionRepository.findByUserId(searchInput, pageable);
        } else if ("suspensionTitle".equals(searchField)) {
            memberPage = suspensionRepository.findBySuspensionTitle(searchInput, pageable);
        } else {
            // 검색 조건이 없는 경우 전체 회원 조회
            memberPage = suspensionRepository.findAll(pageable);
        }

        log.info("Total Elements: {}", memberPage.getTotalElements());

//        return memberPage.getContent().stream()
//                .map(this::convertToSuspensionDto)
//                .collect(Collectors.toList());

        List<SuspensionDto> memberDtos = memberPage.getContent().stream()
                .map(this::convertToSuspensionDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("members", memberDtos);
        response.put("totalPages", memberPage.getTotalPages());

        return response;
    }

    // AdminEntity를 AdminDto로 변환하는 메서드
    private SuspensionDto convertToSuspensionDto(SuspensionEntity suspensionEntity) {

        // 나머지 필드도 설정
        return new SuspensionDto(suspensionEntity.getUserId(),
                suspensionEntity.getSuspensionNo(),
                suspensionEntity.getSuspensionStart(),
                suspensionEntity.getSuspensionTitle(),
                suspensionEntity.getSuspensionContent(),
                suspensionEntity.getSuspensionStatus());

    }

    // 회원 삭제 메서드
    public void deleteSuspensionMember(Long suspensionNo) {
        // userId에 해당하는 회원을 삭제합니다.
        suspensionRepository.deleteById(suspensionNo);
    }

}
