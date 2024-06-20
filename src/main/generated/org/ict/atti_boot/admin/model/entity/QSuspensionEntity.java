package org.ict.atti_boot.admin.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSuspensionEntity is a Querydsl query type for SuspensionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSuspensionEntity extends EntityPathBase<SuspensionEntity> {

    private static final long serialVersionUID = 1134038372L;

    public static final QSuspensionEntity suspensionEntity = new QSuspensionEntity("suspensionEntity");

    public final StringPath suspensionContent = createString("suspensionContent");

    public final NumberPath<Long> suspensionNo = createNumber("suspensionNo", Long.class);

    public final DateTimePath<java.util.Date> suspensionStart = createDateTime("suspensionStart", java.util.Date.class);

    public final StringPath suspensionStatus = createString("suspensionStatus");

    public final StringPath suspensionTitle = createString("suspensionTitle");

    public final StringPath userId = createString("userId");

    public QSuspensionEntity(String variable) {
        super(SuspensionEntity.class, forVariable(variable));
    }

    public QSuspensionEntity(Path<? extends SuspensionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSuspensionEntity(PathMetadata metadata) {
        super(SuspensionEntity.class, metadata);
    }

}

