package org.ict.atti_boot.feed.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeed is a Querydsl query type for Feed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeed extends EntityPathBase<Feed> {

    private static final long serialVersionUID = 1186612119L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeed feed = new QFeed("feed");

    public final StringPath category = createString("category");

    public final StringPath feedContent = createString("feedContent");

    public final DateTimePath<java.time.LocalDateTime> feedDate = createDateTime("feedDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> feedNum = createNumber("feedNum", Integer.class);

    public final NumberPath<Integer> feedReadCount = createNumber("feedReadCount", Integer.class);

    public final StringPath inPublic = createString("inPublic");

    public final ListPath<org.ict.atti_boot.likeHistory.model.entity.LikeHistory, org.ict.atti_boot.likeHistory.model.entity.QLikeHistory> likeHistories = this.<org.ict.atti_boot.likeHistory.model.entity.LikeHistory, org.ict.atti_boot.likeHistory.model.entity.QLikeHistory>createList("likeHistories", org.ict.atti_boot.likeHistory.model.entity.LikeHistory.class, org.ict.atti_boot.likeHistory.model.entity.QLikeHistory.class, PathInits.DIRECT2);

    public final ListPath<org.ict.atti_boot.reply.model.entity.Reply, org.ict.atti_boot.reply.model.entity.QReply> replies = this.<org.ict.atti_boot.reply.model.entity.Reply, org.ict.atti_boot.reply.model.entity.QReply>createList("replies", org.ict.atti_boot.reply.model.entity.Reply.class, org.ict.atti_boot.reply.model.entity.QReply.class, PathInits.DIRECT2);

    public final org.ict.atti_boot.user.jpa.entity.QUser user;

    public QFeed(String variable) {
        this(Feed.class, forVariable(variable), INITS);
    }

    public QFeed(Path<? extends Feed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeed(PathMetadata metadata, PathInits inits) {
        this(Feed.class, metadata, inits);
    }

    public QFeed(Class<? extends Feed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new org.ict.atti_boot.user.jpa.entity.QUser(forProperty("user")) : null;
    }

}

