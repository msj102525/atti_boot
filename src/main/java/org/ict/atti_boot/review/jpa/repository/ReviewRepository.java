package org.ict.atti_boot.review.jpa.repository;


import org.ict.atti_boot.review.jpa.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r, u.nickName FROM Review r JOIN User u ON r.userId = u.userId WHERE r.doctorId = :doctorId")
    Page<Object[]> findByDoctorId(String doctorId, Pageable pageable);

    List<Review> findByDoctorId(String doctorId);
}
