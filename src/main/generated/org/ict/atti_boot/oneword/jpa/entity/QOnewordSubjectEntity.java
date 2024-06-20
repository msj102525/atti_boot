package org.ict.atti_boot.oneword.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOnewordSubjectEntity is a Querydsl query type for OnewordSubjectEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOnewordSubjectEntity extends EntityPathBase<OnewordSubjectEntity> {

    private static final long serialVersionUID = 1812319734L;

    public static final QOnewordSubjectEntity onewordSubjectEntity = new QOnewordSubjectEntity("onewordSubjectEntity");

    public final NumberPath<Integer> owsjNum = createNumber("owsjNum", Integer.class);

    public final StringPath owsjSubject = createString("owsjSubject");

    public final DateTimePath<java.util.Date> owsjWriteDate = createDateTime("owsjWriteDate", java.util.Date.class);

    public final StringPath owsjWriter = createString("owsjWriter");

    public QOnewordSubjectEntity(String variable) {
        super(OnewordSubjectEntity.class, forVariable(variable));
    }

    public QOnewordSubjectEntity(Path<? extends OnewordSubjectEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOnewordSubjectEntity(PathMetadata metadata) {
        super(OnewordSubjectEntity.class, metadata);
    }

}

