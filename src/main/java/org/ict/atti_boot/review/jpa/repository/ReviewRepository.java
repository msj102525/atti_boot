package org.ict.atti_boot.review.jpa.repository;


import org.ict.atti_boot.review.jpa.entity.Review;
import org.ict.atti_boot.review.model.output.StarPointVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r, u.nickName FROM Review r LEFT JOIN User u ON r.userId = u.userId WHERE r.doctorId = :doctorId ORDER BY r.writeDate DESC")
    Page<Object[]> findByDoctorId(String doctorId, Pageable pageable);

    @Query("SELECT AVG(r.starPoint) FROM Review r WHERE r.doctorId = :userId")
    Float findAverageStarPointByUserId(@Param("userId") String userId);

    @Query("SELECT new org.ict.atti_boot.review.model.output.StarPointVo(r.starPoint, COUNT(r)) " +
           "FROM Review r WHERE r.doctorId = :userId GROUP BY r.starPoint")
    List<StarPointVo> findStarPointCountsByUserId(@Param("userId") String userId);

    Page<Review> findByUserId(String userId, Pageable pageable);

    List<Review> findByDoctorId(String doctorId);

    List<Review> findByWriteDateAndUserIdAndDoctorId(Date writeDate, String userId, String doctorId);
}
