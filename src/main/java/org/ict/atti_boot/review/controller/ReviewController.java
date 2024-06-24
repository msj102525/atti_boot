package org.ict.atti_boot.review.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.review.jpa.entity.Review;
import org.ict.atti_boot.review.model.input.ReviewDto;
import org.ict.atti_boot.review.model.output.OutputReview;
import org.ict.atti_boot.review.model.output.ReviewResponse;
import org.ict.atti_boot.review.model.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        log.info(reviewDTO + "프로젝트");
        Review review = reviewService.saveReview(reviewDTO);
        return ResponseEntity.ok(review);
    }


}
