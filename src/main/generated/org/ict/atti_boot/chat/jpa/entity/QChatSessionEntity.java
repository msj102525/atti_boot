package org.ict.atti_boot.chat.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatSessionEntity is a Querydsl query type for ChatSessionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatSessionEntity extends EntityPathBase<ChatSessionEntity> {

    private static final long serialVersionUID = 560192412L;

    public static final QChatSessionEntity chatSessionEntity = new QChatSessionEntity("chatSessionEntity");

    public final NumberPath<Long> chatId = createNumber("chatId", Long.class);

    public final StringPath receiverId = createString("receiverId");

    public final StringPath senderId = createString("senderId");

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public QChatSessionEntity(String variable) {
        super(ChatSessionEntity.class, forVariable(variable));
    }

    public QChatSessionEntity(Path<? extends ChatSessionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatSessionEntity(PathMetadata metadata) {
        super(ChatSessionEntity.class, metadata);
    }

}

