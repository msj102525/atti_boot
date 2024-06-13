package org.ict.atti_boot.feed.repository;

import org.ict.atti_boot.feed.model.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Integer> {
    List<FeedContentVo> findTop5ByOrderByFeedReadCountDesc();

    Page<Feed> findByCategory(String category, Pageable pageable);
}
