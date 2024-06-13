package org.ict.atti_boot.likeHistory.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.likeHistory.model.entity.LikeHistory;
import org.ict.atti_boot.likeHistory.repository.QLikeHistoryRepository;
import org.ict.atti_boot.likeHistory.repository.LikeHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class LikeHistoryService {
    private final LikeHistoryRepository likeHistoryRepository;
    private final QLikeHistoryRepository qlikeHistoryRepository;

    public String postLikeHistory(LikeHistory likeHistory) {
        LikeHistory isUserLiked = likeHistoryRepository.findByUserIdAndFeedNum(likeHistory.getUserId(), likeHistory.getFeedNum());
        if (isUserLiked == null) {
            likeHistory.setLikeHistoryId(qlikeHistoryRepository.selectLastLikeHistoryId() + 1);
            likeHistoryRepository.save(likeHistory);
            return "save";
        } else {
            likeHistoryRepository.delete(isUserLiked);
            return "delete";
        }
    }
}
