package org.ict.atti_boot.review.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.review.jpa.entity.Review;
import org.ict.atti_boot.review.model.input.ReviewDto;
import org.ict.atti_boot.review.model.output.MyReview;
import org.ict.atti_boot.review.model.output.MyReviewResponse;
import org.ict.atti_boot.review.model.output.OutputReview;
import org.ict.atti_boot.review.model.output.ReviewResponse;
import org.ict.atti_boot.review.model.service.ReviewService;
import org.ict.atti_boot.user.model.output.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review")
@Slf4j

public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<ReviewResponse> getMoreReview(@PageableDefault(size = 4, sort = "writeDate", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(name = "id") String doctorId) {
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
        Boolean hasMoreReview = reviewData.hasNext();

        return ResponseEntity.ok(new ReviewResponse(reviewList, hasMoreReview));
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDTO) {
        Review review = reviewService.saveReview(reviewDTO);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/my")
    public ResponseEntity<MyReviewResponse> getMyReview(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sortDir", defaultValue = "DESC") String sortDir) {
        log.info("페이지 : " + page);
        log.info("정렬방향 : " + sortDir);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String id = userDetails.getUserId();
        // 정렬 방향 설정
        Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());

        // 페이지 크기와 정렬 방향을 설정하여 Pageable 객체 생성
        int pageSize = 4; // 고정된 페이지 크기
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, "writeDate"));
        Page<Review> reviewEntityList = reviewService.findByUserId(id, pageable);

        List<MyReview> myReviews = new ArrayList<MyReview>();
        for (Review review : reviewEntityList.getContent()) {
            MyReview myReview = new MyReview(review);
            myReviews.add(myReview);
            log.info(myReview.toString());
        }
        int totalPage = reviewEntityList.getTotalPages();
        MyReviewResponse myReviewResponse = new MyReviewResponse(myReviews, totalPage);

        return ResponseEntity.ok(myReviewResponse);
    }

    @PutMapping
    public ResponseEntity<String> updateReview(@RequestBody ReviewDto reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String id = userDetails.getUserId();
        Review originalReview = reviewService.findByReviewId(reviewDTO.getReviewId());

        String originalUserId = originalReview.getUserId();
        //아이디 확인
        if (!originalUserId.equals(id)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("잘못된 요청입니다.");
        }
        reviewDTO.setSenderId(id);
        reviewDTO.setWriteDate(originalReview.getWriteDate());


        Review review = reviewService.saveReview(reviewDTO);
        if (review == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("수정을 실패했습니다.");
        }

        return ResponseEntity.ok("수정 성공 !");
    }


    @GetMapping("/check")
    public ResponseEntity<List<Review>> checkReview(
            @RequestParam("writeDate") Date writeDate,
            @RequestParam("senderId") String userId,
            @RequestParam("receiverId") String doctorId) {
        List<Review> existingReviews = reviewService.checkReview(writeDate, userId, doctorId);
        return ResponseEntity.ok(existingReviews);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String id = userDetails.getUserId();
        Review originalReview = reviewService.findByReviewId(reviewId);
        //아이디 확인
        if (!originalReview.getUserId().equals(id)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("잘못된 요청입니다.");
        }
        boolean isSuccess = reviewService.deleteReview(reviewId);

        if (isSuccess) {
            return ResponseEntity.ok("삭제되었습니다.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("존재하지 않는 리뷰입니다.");
        }
    }



}
