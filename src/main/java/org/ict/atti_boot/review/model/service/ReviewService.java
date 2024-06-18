package org.ict.atti_boot.review.model.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.review.jpa.entity.Review;
import org.ict.atti_boot.review.jpa.repository.ReviewRepository;
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

    public List<Review> findByDoctorId(String doctorId) {
        return reviewRepository.findByDoctorId(doctorId);
    }

}