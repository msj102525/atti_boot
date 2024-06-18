package org.ict.atti_boot.admin.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.admin.model.dto.AdminDto;
import org.ict.atti_boot.admin.model.entity.AdminEntity;
import org.ict.atti_boot.admin.repository.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // 모든 회원 정보 조회 메서드
//    public List<AdminDto> getAllMembers() {
//
//        List<AdminEntity> users = adminRepository.findAll();
//        return users.stream().map(this::convertToAdminDto).collect(Collectors.toList());
//    }

    public List<AdminDto> getAllMembers(int page, int size, String searchField, String searchInput) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminEntity> memberPage;

        log.info("Page: {}, Size: {}, Search Field: {}, Search Input: {}", page, size, searchField, searchInput);

        // 검색 필드에 따라 적절한 메서드 호출
        if ("userId".equals(searchField)) {
            memberPage = adminRepository.findByUserId(searchInput, pageable);
        } else if ("userName".equals(searchField)) {
            memberPage = adminRepository.findByUserName(searchInput, pageable);
        } else {
            // 검색 조건이 없는 경우 전체 회원 조회
            memberPage = adminRepository.findAll(pageable);
        }

        log.info("Total Elements: {}", memberPage.getTotalElements());

        return memberPage.getContent().stream()
                .map(this::convertToAdminDto)
                .collect(Collectors.toList());
    }



    // AdminEntity를 AdminDto로 변환하는 메서드
    private AdminDto convertToAdminDto(AdminEntity adminEntity) {

        // 나머지 필드도 설정
        return new AdminDto(adminEntity.getUserId(),
                adminEntity.getUserName(),
                adminEntity.getNickName(),
                adminEntity.getEmail());
    }

    // 회원 삭제 메서드
    public void deleteMember(String userId) {
        // userId에 해당하는 회원을 삭제합니다.
        adminRepository.deleteById(userId);
    }

//    public void updateMember(String userId, AdminDto adminDto) {
//        Optional<AdminEntity> optionalMember = adminRepository.findById(userId);
//        if (optionalMember.isPresent()) {
//            AdminEntity adminEntity = optionalMember.get();
//            // 클라이언트로부터 받은 회원 정보를 엔티티로 업데이트
//            adminEntity.setUserName(adminDto.getUserName());
//            adminEntity.setNickName(adminDto.getNickName());
//            adminEntity.setEmail(adminDto.getEmail());
//            adminRepository.save(adminEntity);
//        } else {
//            throw new RuntimeException("Member not found with ID: " + userId);
//        }
//    }

    public void updateMember(String userId, AdminDto adminDto) {
        // 디버깅을 위해 adminDto를 콘솔에 출력
        System.out.println("Updating member with data: " + adminDto);

        AdminEntity adminEntity = adminRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        adminEntity.setUserName(adminDto.getUserName());
        adminEntity.setNickName(adminDto.getNickName());
        adminEntity.setEmail(adminDto.getEmail());

        adminRepository.save(adminEntity);
    }




}
