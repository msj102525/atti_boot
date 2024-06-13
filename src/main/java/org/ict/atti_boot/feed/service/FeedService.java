package org.ict.atti_boot.feed.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.feed.model.entity.Feed;
import org.ict.atti_boot.feed.repository.FeedContentVo;
import org.ict.atti_boot.feed.repository.FeedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

    public Feed save(Feed feedEntity) {
        return feedRepository.save(feedEntity);
    }

    public List<FeedContentVo> selectTop5Feed() {
        return feedRepository.findTop5ByOrderByFeedReadCountDesc();
    }

    public Page<Feed> selectAllFeeds(Pageable pageable, String category) {
        if (category == null || category.isEmpty()) {
            return feedRepository.findAll(pageable);
        } else {
            return feedRepository.findByCategory(category, pageable);
        }
    }
}
