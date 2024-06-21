package org.ict.atti_boot.doctor.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.jpa.entity.Career;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;
import org.ict.atti_boot.doctor.jpa.entity.Education;
import org.ict.atti_boot.doctor.model.DoctorUpdateInput;
import org.ict.atti_boot.doctor.model.dto.EmailRequest;
import org.ict.atti_boot.doctor.model.outputVo.DoctorDetail;
import org.ict.atti_boot.doctor.model.outputVo.DoctorUpdateVo;
import org.ict.atti_boot.doctor.model.service.CareerService;
import org.ict.atti_boot.doctor.model.service.DoctorService;
import org.ict.atti_boot.doctor.model.service.EducationService;
import org.ict.atti_boot.doctor.model.service.TagService;
import org.ict.atti_boot.review.jpa.entity.Review;
import org.ict.atti_boot.review.model.output.StarPointVo;
import org.ict.atti_boot.review.model.service.ReviewService;

import org.ict.atti_boot.review.model.output.OutputReview;
import org.ict.atti_boot.user.model.output.CustomUserDetails;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;
    private final ReviewService reviewService;
    private final CareerService careerService;
    private final EducationService educationService;
    private final TagService tagService;

    public DoctorController(DoctorService doctorService, ReviewService reviewService,CareerService careerService,EducationService educationService,TagService tagService) {
        this.doctorService = doctorService;
        this.reviewService = reviewService;
        this.careerService = careerService;
        this.educationService = educationService;
        this.tagService = tagService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getDoctors(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(doctorService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetail> getDoctorDetail(@PageableDefault(size = 4, sort = "writeDate", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable(name = "id", required = false) String doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Page<Object[]> reviewData = reviewService.findByDoctorId(doctorId, pageable);
        //리뷰 아웃풋 DTO 객체로 바꾸깅
        Boolean hasMoreReview = reviewData.hasNext();
        List<OutputReview> reviewList = reviewData.getContent().stream()
                .map(objects -> {
                    Review review = (Review) objects[0];
                    String nickName = (String) objects[1];
                    return OutputReview.builder()
                            .nickName(nickName)
                            .content(review.getContent())
                            .starPoint(review.getStarPoint())
                            .build();
                })
                .collect(Collectors.toList());


        // 평점분포 맵만들기
       List<StarPointVo> starPointList  = reviewService.findStarPointCountsByUserId(doctorId);
        // 평균 점수 계산
        double averageScore = reviewService.getAverageRating(doctorId);

        Map<Integer,Integer> ratingCount = new HashMap<Integer,Integer>();
        for(StarPointVo starPointVo : starPointList) {
            ratingCount.put(starPointVo.getStarPoint(),(int)starPointVo.getCount());
        }

        Set<Career> careers = careerService.getCareersById(doctorId);
        Set<Education> educations = educationService.getEducationsById(doctorId);
        Set<DoctorTag> tags = tagService.getTagsById(doctorId);

        DoctorDetail doctorDetail = new DoctorDetail(doctor, reviewList, ratingCount, averageScore, hasMoreReview,careers,educations,tags);
        log.info(doctorDetail.toString());
        return ResponseEntity.ok(doctorDetail);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDoctors(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(name = "selectedTags", required = false) List<String> tags,
                                                             @RequestParam(name = "keyword", required = false) String name,
                                                             @RequestParam(name = "gender", required = false) Character gender) {
        long tagCount = tags.size();
        return ResponseEntity.ok(doctorService.findByAllConditions(name, tags, tagCount, gender, pageable));
    }

    @GetMapping("/mypage")
    public ResponseEntity<DoctorUpdateVo> getDoctorProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String doctorId = userDetails.getUserId();

        DoctorUpdateVo doctorUpdateVo = doctorService.getDoctorMyPageById(doctorId);


        return ResponseEntity.ok(doctorUpdateVo);
    }


    @PutMapping("/mypage")
    public ResponseEntity<String> updateDoctorProfile(
            @RequestPart("doctorData") DoctorUpdateInput doctorUpdateInput,     // 이미지 파일과 JSON데이터를 함께 처리하기위해 RequestPart 사용
            @RequestPart(value = "hospitalImage", required = false) MultipartFile hospitalImage) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String doctorId = userDetails.getUserId();

        // 파일 저장 경로 설정
        String uploadDir = "src/main/resources/hospitalprofile/";
        doctorUpdateInput.setDoctorId(doctorId);
        Doctor doctor = doctorService.getDoctorById(doctorId);
        String originalFile = doctor.getHospitalImageUrl();
        String fileName;
        // 프로필 사진URL을 DB에서 뽑아옴
        //프로필 사진파일이 왔다.
        //사진을 바꿨거나? 새로 등록했거나 ?
        if (!(hospitalImage == null)) {
            // 파일 확장자 추출
            String originalFilename = hospitalImage.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 파일 이름을 UUID로 변경 중복을 피하기 위함
            String newFileName = UUID.randomUUID() + fileExtension;
            // 저장 경로 생성
            Path filePath = Paths.get(uploadDir + newFileName);
            // 파일 저장
            Files.write(filePath, hospitalImage.getBytes());
            fileName = "/images/" + filePath.getFileName().toString();
            if (originalFile != null) {
                //이미 있는데 바꿨다? 원래파일 지우기
                filePath = Paths.get(uploadDir + originalFile.substring(8));
                Files.delete(filePath);
            }
        } else {
            log.info(doctor.getHospitalImageUrl());
            log.info(doctorUpdateInput.getHospitalFileName());
            //프로필사진이 안왔다
            //근데 VO에는 들어있다?  ==>> 그냥 유지
            //근데 VO도 빈채로 왔다? 원래 있었는데 지웠다는 소리기때문에
            //원래 파일은 삭제한다.
            if (doctorUpdateInput.getHospitalFileName() == null) {
                Path filePath = Paths.get(uploadDir + originalFile.substring(8));
                Files.delete(filePath);
                fileName = null;
            } else {
                fileName = originalFile;
            }

        }


        doctorService.updateDoctor(doctorUpdateInput, fileName);

        return ResponseEntity.ok("병원정보 수정 완료 !");
    }


    @PostMapping("/mail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            request.setNumberOfMembers(doctorService.findAllUserCount());
            doctorService.sendEmail(request);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }
}
