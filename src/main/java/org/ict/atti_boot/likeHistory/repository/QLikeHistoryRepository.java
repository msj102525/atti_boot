package org.ict.atti_boot.likeHistory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.ict.atti_boot.likeHistory.model.entity.QLikeHistory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QLikeHistoryRepository {

    private final JPAQueryFactory queryFactory;
    QLikeHistory likeHistory = QLikeHistory.likeHistory;

    public int selectLastLikeHistoryId() {
        Integer lastId = queryFactory
                .select(likeHistory.likeHistoryId.max())
                .from(likeHistory)
                .fetchOne();

        return lastId != null ? lastId : 1;
    }
}
