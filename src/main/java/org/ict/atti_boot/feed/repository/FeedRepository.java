package org.ict.atti_boot.feed.repository;

import org.hibernate.usertype.UserType;
import org.ict.atti_boot.feed.model.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Integer> {
    List<FeedContentVo> findTop5ByOrderByFeedReadCountDesc();

    Page<Feed> findByCategory(String category, Pageable pageable);

    @Query("SELECT f FROM Feed f LEFT JOIN f.likeHistories lh GROUP BY f.feedNum, f.feedContent, f.feedDate, f.feedReadCount, f.category, f.inPublic, f.user ORDER BY COUNT(lh) DESC, f.feedDate DESC")
    Page<Feed> findAllByOrderByLikeHistoriesDescAndFeedDateDesc(Pageable pageable);

    @Query("SELECT f FROM Feed f LEFT JOIN f.likeHistories lh WHERE f.category = :category GROUP BY f.feedNum, f.feedContent, f.feedDate, f.feedReadCount, f.category, f.inPublic, f.user ORDER BY COUNT(lh) DESC, f.feedDate DESC")
    Page<Feed> findByCategoryOrderByLikeHistoriesDescAndFeedDateDesc(@Param("category") String category, Pageable pageable);

    @Query("SELECT f FROM Feed f JOIN FETCH f.replies r JOIN r.user u WHERE u.userType = 'D'")
    Page<Feed> findAllFeedsWithRepliesByUserType(Pageable pageable );

    @Query("SELECT f FROM Feed f JOIN FETCH f.replies r JOIN r.user u WHERE u.userType = 'D' and f.category = :category ")
    Page<Feed> findAllFeedsByCategoryWithRepliesByUserType(String category,Pageable pageable );

}
