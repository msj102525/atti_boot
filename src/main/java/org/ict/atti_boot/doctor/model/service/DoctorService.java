//package org.ict.atti_boot.doctor.model.service;
//
//
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.ict.atti_boot.doctor.jpa.entity.Doctor;
//import org.ict.atti_boot.doctor.jpa.repository.DoctorRepository;
//import org.ict.atti_boot.doctor.model.dto.DoctorDto;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//@Slf4j
//public class DoctorService {
//
//    private final DoctorRepository doctorRepository;
//
//    public DoctorService(DoctorRepository doctorRepository) {
//        this.doctorRepository = doctorRepository;
//    }
//
//    public List<DoctorDto> findAllDoctor(){
//        return doctorRepository.findAllWithUsers();
//    }
//
//    private List<DoctorDto> toDtoList(List<Doctor> doctors){
//        List<DoctorDto> doctorDtos = new ArrayList<>();
//        for(Doctor doctor : doctors){
//            DoctorDto dto = doctor.toDto();
//            doctorDtos.add(dto);
//        }
//        return doctorDtos;
//    }
//
//    public Map<String,Object> findAll(Pageable pageable){
//
//        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
//        return constructResponse(doctorPage);
//    }
//
//     public Map<String, Object> findByName(String name, Pageable pageable) {
//        Page<Doctor> doctorPage = doctorRepository.findByUserNameContaining(name, pageable);
//        Map<String, Object> doctors = new HashMap<>();
//        List<DoctorDto> doctorDtos = new ArrayList<>();
//        List<Doctor> doctorEntityList = doctorPage.getContent();
//        for(Doctor d : doctorEntityList){
//            DoctorDto dto = new DoctorDto(d);
//            doctorDtos.add(dto);
//        }
//        doctors.put("count", doctorPage.getTotalElements());
//        doctors.put("pages", doctorPage.getTotalPages());
//        doctors.put("doctors", doctorDtos);
//        return doctors;
//    }
//
//
////    public Map<String, Object> searchByAll(Pageable pageable, String name, String gender, List<String> tags) {
////    }
////
////    public Map<String, Object> searchByTagsAndGender(Pageable pageable, String gender, List<String> tags) {
////    }
////
////    public Map<String, Object> searchByTagsAndName(Pageable pageable, String name, List<String> tags) {
////    }
////
////    public Map<String, Object> searchByGenderAndName(Pageable pageable, String name, String gender) {
////    }
////
////    public Map<String, Object> findByGender(String gender, Pageable pageable) {
////    }
//
//    public Map<String, Object> findByTags(List<String> tags, Pageable pageable) {
//        Page<Doctor> doctorPage = doctorRepository.findByTagsIn(tags, pageable);
//        return constructResponse(doctorPage);
//    }
//
//
//        private Map<String, Object> constructResponse(Page<Doctor> doctorPage) {
//        Map<String, Object> response = new HashMap<>();
//        List<DoctorDto> doctorDtos = new ArrayList<>();
//        List<Doctor> doctorEntityList = doctorPage.getContent();
//        for (Doctor d : doctorEntityList) {
//            DoctorDto dto = new DoctorDto(d);
//            doctorDtos.add(dto);
//        }
//        response.put("count", doctorPage.getTotalElements());
//        response.put("pages", doctorPage.getTotalPages());
//        response.put("doctors", doctorDtos);
//        return response;
//    }
//
//    /*public NoticeBoard save(NoticeBoard noticeBoard, List<NoticeFile> noticeFiles) {
//        NoticeBoard savedNoticeBoard = noticeRepository.save(noticeBoard);
//        if (noticeFiles != null && !noticeFiles.isEmpty()) {
//            for (NoticeFile noticeFile : noticeFiles) {
//                noticeFile.setNoticeId(savedNoticeBoard.getId().toString());
//                noticeFileRepository.save(noticeFile);
//            }
//        }
//        return savedNoticeBoard;
//    }
//
//    public Map<String, Object> getNoticesWithPinnedSeparate(String category, String inactiveStatus, String title, int page, int size) {
//        List<NoticeBoard> pinnedNoticeBoards = noticeRepository.findTop5ByCategoryAndIsPinnedTrueOrderByCreatedAtDesc(category);
//        List<NoticeBoardResponseDto> pinnedNotices = pinnedNoticeBoards.stream()
//                .map(NoticeBoardResponseDto::new)
//                .collect(Collectors.toList());
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        Page<NoticeBoard> noticePage = noticeRepository.findByCategoryAndStatusAndTitleContainingIgnoreCase(
//                category, inactiveStatus, title, pageable);
//        Page<NoticeBoardResponseDto> noticePageDto = noticePage.map(NoticeBoardResponseDto::new);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("pinnedNotices", pinnedNotices);
//        response.put("regularNotices", noticePageDto.getContent());
//        response.put("currentPage", noticePageDto.getNumber());
//        response.put("totalItems", noticePageDto.getTotalElements());
//        response.put("totalPages", noticePageDto.getTotalPages());
//
//        return response;
//    }
//
//    public void incrementReadCount(String postId) {
//        NoticeBoard notice = noticeRepository.findById(UUID.fromString(postId))
//                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));
//        notice.setViews(notice.getViews() + 1);
//        noticeRepository.save(notice);
//    }
//
//    public String toggleLike(UserNoticeLikeId id) {
//        boolean exists = userNoticeLikesRepository.existsById(id);
//        if (exists) {
//            userNoticeLikesRepository.deleteById(id);
//            decrementLikeCount(String.valueOf(id.getNoticeId()));
//            return "Like successfully removed.";
//        } else {
//            UserNoticeLike likeEntry = new UserNoticeLike();
//            likeEntry.setId(id);
//            userNoticeLikesRepository.save(likeEntry);
//            incrementLikeCount(String.valueOf(id.getNoticeId()));
//            return "Like successfully added.";
//        }
//    }
//
//    private void incrementLikeCount(String noticeId) {
//        NoticeBoard notice = noticeRepository.findById(UUID.fromString(noticeId))
//                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID: " + noticeId));
//        notice.setLikeCount(notice.getLikeCount() + 1);
//        noticeRepository.save(notice);
//    }
//
//    private void decrementLikeCount(String noticeId) {
//        NoticeBoard notice = noticeRepository.findById(UUID.fromString(noticeId))
//                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID: " + noticeId));
//        int currentLikes = notice.getLikeCount();
//        notice.setLikeCount(Math.max(0, currentLikes - 1));
//        noticeRepository.save(notice);
//    }*/
//}
