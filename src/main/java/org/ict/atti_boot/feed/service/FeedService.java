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
import java.util.Optional;

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

    public Page<Feed> selectAllFeedsByLikeCount(Pageable pageable, String category) {
        if (category == null || category.isEmpty()) {
            return feedRepository.findAllByOrderByLikeHistoriesDescAndFeedDateDesc(pageable);
        } else {
            return feedRepository.findByCategoryOrderByLikeHistoriesDescAndFeedDateDesc(category, pageable);
        }
    }

    public Page<Feed> selectAllFeedsHasDocterReply(Pageable pageable, String category) {
        if (category == null || category.isEmpty()) {
            return feedRepository.findAllFeedsWithRepliesByUserType(pageable);
        } else {
            return feedRepository.findAllFeedsByCategoryWithRepliesByUserType(category, pageable);
        }
    }

    public Page<Feed> selectAllFeedsBySearchData(Pageable pageable, String category, String searchData) {
        log.info("FeedService : " + searchData);
        return feedRepository.findAllByFeedContentContaining(pageable, searchData);
    }

    public Feed selectFeedById(int feedNum) {
        Feed feed = feedRepository.findById(feedNum)
                .orElseThrow(() -> new IllegalArgumentException("Invalid feedNum: " + feedNum));
        feed.setFeedReadCount(feed.getFeedReadCount() + 1);
        return feedRepository.save(feed);

    }

    public void deleteFeedById(int feedNum) {
        Feed feed = feedRepository.findById(feedNum)
                .orElseThrow(() -> new IllegalArgumentException("Invalid feedNum: " + feedNum));
        feedRepository.delete(feed);
    }

}
