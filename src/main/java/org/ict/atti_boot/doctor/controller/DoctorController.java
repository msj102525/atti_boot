package org.ict.atti_boot.doctor.controller;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;
import org.ict.atti_boot.doctor.model.dto.EmailRequest;
import org.ict.atti_boot.doctor.model.outputVo.DoctorDetail;
import org.ict.atti_boot.doctor.model.service.DoctorService;
import org.ict.atti_boot.review.jpa.entity.Review;
import org.ict.atti_boot.review.model.ReviewService;

import org.ict.atti_boot.review.model.output.OutputReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;
    private final ReviewService reviewService;

    public DoctorController(DoctorService doctorService, ReviewService reviewService) {
        this.doctorService = doctorService;
        this.reviewService = reviewService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getDoctors(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(doctorService.findAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetail> getDoctorDetail(@PageableDefault(sort = "writeDate", direction = Sort.Direction.ASC) Pageable pageable,@PathVariable(name="id",required = false) String doctorId) {
        Map<String, Object> response = new HashMap<>();
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Page<Object[]> reviewData = reviewService.findByDoctorId(doctorId, pageable);
        //리뷰 아웃풋 DTO 객체로 바꾸깅
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

         List<Review> totalReviewList = reviewService.findByDoctorId(doctorId);
        // 평균 변수
        double totalScore = 0.0;
         // 평점분포 맵만들기
        Map<Integer, Integer> ratingCount = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            ratingCount.put(i, 0);
        }
         for (Review review : totalReviewList) {
            int score = review.getStarPoint();
            ratingCount.put(score, ratingCount.get(score) + 1);
            totalScore += score;
        }
        // 평균 점수 계산
        double averageScore = totalReviewList.isEmpty() ? 0 : totalScore / totalReviewList.size();
        DoctorDetail doctorDetail = new DoctorDetail(doctor, reviewList, ratingCount, averageScore);
        return ResponseEntity.ok(doctorDetail);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDoctors(@PageableDefault(size = 4, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(name="selectedTags",required = false) List<String> tags,
            @RequestParam(name="keyword", required = false) String name,
            @RequestParam(name="gender", required = false) Character gender) {
        long tagCount = tags.size();
        return ResponseEntity.ok(doctorService.findByAllConditions(name, tags, tagCount, gender,pageable));
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
