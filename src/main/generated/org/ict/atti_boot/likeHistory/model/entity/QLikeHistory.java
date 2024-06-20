package org.ict.atti_boot.likeHistory.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLikeHistory is a Querydsl query type for LikeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeHistory extends EntityPathBase<LikeHistory> {

    private static final long serialVersionUID = 489956107L;

    public static final QLikeHistory likeHistory = new QLikeHistory("likeHistory");

    public final NumberPath<Integer> feedNum = createNumber("feedNum", Integer.class);

    public final NumberPath<Integer> likeHistoryId = createNumber("likeHistoryId", Integer.class);

    public final StringPath userId = createString("userId");

    public QLikeHistory(String variable) {
        super(LikeHistory.class, forVariable(variable));
    }

    public QLikeHistory(Path<? extends LikeHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLikeHistory(PathMetadata metadata) {
        super(LikeHistory.class, metadata);
    }

}

