package org.ict.atti_boot.likeHistory.repository;

import org.ict.atti_boot.likeHistory.model.entity.LikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Integer> {
    LikeHistory findByUserIdAndFeedNum(String userId, int feedNum);
}
