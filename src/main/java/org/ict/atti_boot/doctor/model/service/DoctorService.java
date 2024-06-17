package org.ict.atti_boot.doctor.model.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.jpa.entity.Career;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.jpa.entity.Education;
import org.ict.atti_boot.doctor.jpa.repository.CareerRepository;
import org.ict.atti_boot.doctor.jpa.repository.DoctorRepository;
import org.ict.atti_boot.doctor.jpa.repository.EducationRepository;
import org.ict.atti_boot.doctor.jpa.specification.DoctorSpecifications;
import org.ict.atti_boot.doctor.model.outputVo.DoctorDto;
import org.ict.atti_boot.doctor.model.dto.EmailRequest;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.*;

@Service
@Transactional
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final EducationRepository educationRepository;
    private final CareerRepository careerRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    public DoctorService(DoctorRepository doctorRepository, EducationRepository educationRepository, CareerRepository careerRepository, JavaMailSender mailSender, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.educationRepository = educationRepository;
        this.careerRepository = careerRepository;
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

//    public List<DoctorDto> findAllDoctor() {
//        return doctorRepository.findAllWithUsers();
//    }


    public Map<String, Object> findAll(Pageable pageable) {

        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
        return constructResponse(doctorPage);
    }

//    public Map<String, Object> findByName(String name, Pageable pageable) {
//        Page<Doctor> doctorPage = doctorRepository.findByUserNameContaining(name, pageable);
//        return constructResponse(doctorPage);
//    }
//
//    public Map<String, Object> findByGender(Character gender, Pageable pageable) {
//        Page<Doctor> doctorPage = doctorRepository.findByGenderContaining(gender, pageable);
//        return constructResponse(doctorPage);
//    }

    //
    public List<String> getEducationsByDoctorId(String doctorId) {
        List<Education> educations = educationRepository.findByUserId(doctorId);
        List<String> eduList = new ArrayList<>();
        for (Education education : educations) {
            eduList.add(education.getEducation());
        }
        return eduList;
    }

    public List<String> getCareersByDoctorId(String doctorId) {
        List<Career> careers = careerRepository.findByUserId(doctorId);
        List<String> careerList = new ArrayList<>();
        for (Career career : careers) {
            careerList.add(career.getCareer());
        }
        return careerList;
    }

//    public Set<Education> getEducationsByUserId(String userId) {
//        Doctor doctor = doctorRepository.findByUserId(userId);
//        log.info(doctor.getEducations().toString());
//        return doctor.getEducations();
//    }


//Specification Test

    public Map<String, Object> findByAllConditions(String name, List<String> tags, long tagCount, Character gender, Pageable pageable) {
        Specification<Doctor> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(DoctorSpecifications.userNameContaining(name));
        }
        if (tags != null && !tags.isEmpty()) {
            spec = spec.and(DoctorSpecifications.tagsIn(tags));
        }
        if (gender != null) {
            spec = spec.and(DoctorSpecifications.genderContaining(gender));
        }
        return constructResponse(doctorRepository.findAll(spec, pageable));
    }
//SpecificationTestEnd


//    public Map<String, Object> searchByAll(Pageable pageable, String name, String gender, List<String> tags) {
//    }
//
//    public Map<String, Object> searchByTagsAndGender(Pageable pageable, String gender, List<String> tags) {
//    }
//
//    public Map<String, Object> searchByTagsAndName(Pageable pageable, String name, List<String> tags) {
//    }
//
//    public Map<String, Object> searchByGenderAndName(Pageable pageable, String name, String gender) {
//    }
//
//    public Map<String, Object> findByGender(String gender, Pageable pageable) {
//    }

    public Map<String, Object> findByTags(List<String> tags, Pageable pageable) {
        long tagCount = tags.size();
        Page<Doctor> doctorPage = doctorRepository.findByTagsIn(tags, tagCount, pageable);
        return constructResponse(doctorPage);
    }


    private Map<String, Object> constructResponse(Page<Doctor> doctorPage) {
        Map<String, Object> response = new HashMap<>();
        List<DoctorDto> doctorDtos = new ArrayList<>();
        List<Doctor> doctorEntityList = doctorPage.getContent();
        for (Doctor d : doctorEntityList) {
            DoctorDto dto = new DoctorDto(d);
            doctorDtos.add(dto);
        }
        response.put("count", doctorPage.getTotalElements());
        response.put("pages", doctorPage.getTotalPages());
        response.put("doctors", doctorDtos);
        return response;
    }

    public Doctor getDoctorById(String doctorId) {
        return doctorRepository.findByUserId(doctorId);
    }


      public void sendEmail(EmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            log.info(request.toString());
            helper.setTo(request.getEmail());
            helper.setSubject("Atti에 방문하신걸 진심으로 환영합니다." + request.getDoctorName()+"님");

            // HTML 콘텐츠 작성
            String htmlContent = "<div style=\"background-color: #8ab68a; padding: 40px; margin: 20px auto; border-radius: 40px; max-width: 800px;\">"
                + "<div style=\"text-align: center; margin-bottom: 40px; color: white;\">"
                + "<h1>저희 Atti 회원가입을 환영합니다!</h1>"
                + "</div>"
                + "<div style=\"display: flex; flex-wrap: wrap; align-items: center;\">"
                + "<div style=\"flex: 1; padding: 10px; text-align: center;\">"
                + "<div style=\"background-color: #ffffff; padding: 20px; border-radius: 20px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);\">"
                + "<p style=\"font-size: 18px; color: #333;\">"
                + "지금 <strong style=\"color: #ff6f61;\">"+request.getNumberOfMembers()+"</strong>명의 회원이 전문가님의 도움을 기다리고 있어요!"
                + "</p>"
                + "<h3 style=\"font-size: 24px; margin-top: 20px;\">"
                + "인증코드: <strong>"+request.getCode()+"</strong>"
                + "</h3>"
                + "</div>"
                + "</div>"
                + "<div style=\"flex: 1; text-align: center;\">"
               + "<img src=\"cid:mailForDoctor\" style=\"width: 80%; height: auto;\" />"
                + "</div>"
                + "</div>"
                + "</div>";

            helper.setText(htmlContent, true);
            // 이미지 첨부 (이미지를 인라인으로 추가)
            helper.addInline("mailForDoctor", new FileSystemResource("src/main/img/mailForDoctor.png"));

            mailSender.send(message);
            log.info("메일전송 완료!");
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public long findAllUserCount() {
        return userRepository.count();
    }

    /*public NoticeBoard save(NoticeBoard noticeBoard, List<NoticeFile> noticeFiles) {
        NoticeBoard savedNoticeBoard = noticeRepository.save(noticeBoard);
        if (noticeFiles != null && !noticeFiles.isEmpty()) {
            for (NoticeFile noticeFile : noticeFiles) {
                noticeFile.setNoticeId(savedNoticeBoard.getId().toString());
                noticeFileRepository.save(noticeFile);
            }
        }
        return savedNoticeBoard;
    }

    public Map<String, Object> getNoticesWithPinnedSeparate(String category, String inactiveStatus, String title, int page, int size) {
        List<NoticeBoard> pinnedNoticeBoards = noticeRepository.findTop5ByCategoryAndIsPinnedTrueOrderByCreatedAtDesc(category);
        List<NoticeBoardResponseDto> pinnedNotices = pinnedNoticeBoards.stream()
                .map(NoticeBoardResponseDto::new)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<NoticeBoard> noticePage = noticeRepository.findByCategoryAndStatusAndTitleContainingIgnoreCase(
                category, inactiveStatus, title, pageable);
        Page<NoticeBoardResponseDto> noticePageDto = noticePage.map(NoticeBoardResponseDto::new);

        Map<String, Object> response = new HashMap<>();
        response.put("pinnedNotices", pinnedNotices);
        response.put("regularNotices", noticePageDto.getContent());
        response.put("currentPage", noticePageDto.getNumber());
        response.put("totalItems", noticePageDto.getTotalElements());
        response.put("totalPages", noticePageDto.getTotalPages());

        return response;
    }

    public void incrementReadCount(String postId) {
        NoticeBoard notice = noticeRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));
        notice.setViews(notice.getViews() + 1);
        noticeRepository.save(notice);
    }

    public String toggleLike(UserNoticeLikeId id) {
        boolean exists = userNoticeLikesRepository.existsById(id);
        if (exists) {
            userNoticeLikesRepository.deleteById(id);
            decrementLikeCount(String.valueOf(id.getNoticeId()));
            return "Like successfully removed.";
        } else {
            UserNoticeLike likeEntry = new UserNoticeLike();
            likeEntry.setId(id);
            userNoticeLikesRepository.save(likeEntry);
            incrementLikeCount(String.valueOf(id.getNoticeId()));
            return "Like successfully added.";
        }
    }

    private void incrementLikeCount(String noticeId) {
        NoticeBoard notice = noticeRepository.findById(UUID.fromString(noticeId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID: " + noticeId));
        notice.setLikeCount(notice.getLikeCount() + 1);
        noticeRepository.save(notice);
    }

    private void decrementLikeCount(String noticeId) {
        NoticeBoard notice = noticeRepository.findById(UUID.fromString(noticeId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID: " + noticeId));
        int currentLikes = notice.getLikeCount();
        notice.setLikeCount(Math.max(0, currentLikes - 1));
        noticeRepository.save(notice);
    }*/
}
