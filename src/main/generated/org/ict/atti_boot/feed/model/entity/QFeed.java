package org.ict.atti_boot.feed.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFeed is a Querydsl query type for Feed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeed extends EntityPathBase<Feed> {

    private static final long serialVersionUID = 1186612119L;

    public static final QFeed feed = new QFeed("feed");

    public final StringPath category = createString("category");

    public final StringPath feedContent = createString("feedContent");

    public final DateTimePath<java.time.LocalDateTime> feedDate = createDateTime("feedDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> feedNum = createNumber("feedNum", Integer.class);

    public final NumberPath<Integer> feedReadCount = createNumber("feedReadCount", Integer.class);

    public final StringPath inPublic = createString("inPublic");

    public final StringPath userId = createString("userId");

    public QFeed(String variable) {
        super(Feed.class, forVariable(variable));
    }

    public QFeed(Path<? extends Feed> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFeed(PathMetadata metadata) {
        super(Feed.class, metadata);
    }

}

