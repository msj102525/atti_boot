package org.ict.atti_boot.reply.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReply extends EntityPathBase<Reply> {

    private static final long serialVersionUID = 1981680299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReply reply = new QReply("reply");

    public final NumberPath<Integer> feedNum = createNumber("feedNum", Integer.class);

    public final StringPath replyContent = createString("replyContent");

    public final DateTimePath<java.time.LocalDateTime> replyDate = createDateTime("replyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> replyLev = createNumber("replyLev", Integer.class);

    public final NumberPath<Integer> replyNum = createNumber("replyNum", Integer.class);

    public final NumberPath<Integer> replyReplyRef = createNumber("replyReplyRef", Integer.class);

    public final NumberPath<Integer> replySeq = createNumber("replySeq", Integer.class);

    public final org.ict.atti_boot.user.jpa.entity.QUser user;

    public QReply(String variable) {
        this(Reply.class, forVariable(variable), INITS);
    }

    public QReply(Path<? extends Reply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReply(PathMetadata metadata, PathInits inits) {
        this(Reply.class, metadata, inits);
    }

    public QReply(Class<? extends Reply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new org.ict.atti_boot.user.jpa.entity.QUser(forProperty("user")) : null;
    }

}

