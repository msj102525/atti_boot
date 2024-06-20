package org.ict.atti_boot.admin.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNoticeAdminVersionEntity is a Querydsl query type for NoticeAdminVersionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeAdminVersionEntity extends EntityPathBase<NoticeAdminVersionEntity> {

    private static final long serialVersionUID = 813645832L;

    public static final QNoticeAdminVersionEntity noticeAdminVersionEntity = new QNoticeAdminVersionEntity("noticeAdminVersionEntity");

    public final StringPath boardContent = createString("boardContent");

    public final NumberPath<Long> boardNum = createNumber("boardNum", Long.class);

    public final StringPath boardTitle = createString("boardTitle");

    public final StringPath boardWriter = createString("boardWriter");

    public QNoticeAdminVersionEntity(String variable) {
        super(NoticeAdminVersionEntity.class, forVariable(variable));
    }

    public QNoticeAdminVersionEntity(Path<? extends NoticeAdminVersionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNoticeAdminVersionEntity(PathMetadata metadata) {
        super(NoticeAdminVersionEntity.class, metadata);
    }

}

