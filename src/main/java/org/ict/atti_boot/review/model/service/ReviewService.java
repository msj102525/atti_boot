package org.ict.atti_boot.review.model.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.review.jpa.entity.Review;
import org.ict.atti_boot.review.jpa.repository.ReviewRepository;
import org.ict.atti_boot.review.model.input.ReviewDto;
import org.ict.atti_boot.review.model.output.StarPointVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Page<Object[]> findByDoctorId(String doctorId, Pageable pageable) {
        return reviewRepository.findByDoctorId(doctorId, pageable);
    }

    public Float getAverageRating(String doctorId) {
        return reviewRepository.findAverageStarPointByUserId(doctorId);
    }

    public List<StarPointVo> findStarPointCountsByUserId(String doctorId){
        log.info(doctorId);
        List<StarPointVo> test = reviewRepository.findStarPointCountsByUserId(doctorId);
        log.info(test.toString());
        return test;
    }


    public List<Review> findByDoctorId(String doctorId) {
        return reviewRepository.findByDoctorId(doctorId);
    }

    public Review saveReview(ReviewDto reviewDTO) {
        Review review = Review.builder()
                .starPoint(reviewDTO.getRating())
                .content(reviewDTO.getReview())
                .writeDate(reviewDTO.getWriteDate())
                .userId(reviewDTO.getSenderId())
                .doctorId(reviewDTO.getReceiverId())
                .build();
        return reviewRepository.save(review);
    }



}
