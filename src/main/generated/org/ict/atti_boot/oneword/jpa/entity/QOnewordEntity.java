package org.ict.atti_boot.oneword.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOnewordEntity is a Querydsl query type for OnewordEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOnewordEntity extends EntityPathBase<OnewordEntity> {

    private static final long serialVersionUID = -1035727876L;

    public static final QOnewordEntity onewordEntity = new QOnewordEntity("onewordEntity");

    public final StringPath owContent = createString("owContent");

    public final NumberPath<Integer> owNum = createNumber("owNum", Integer.class);

    public final NumberPath<Integer> owRcount = createNumber("owRcount", Integer.class);

    public final NumberPath<Integer> owsjNum = createNumber("owsjNum", Integer.class);

    public final DateTimePath<java.util.Date> owWriteDate = createDateTime("owWriteDate", java.util.Date.class);

    public final StringPath owWriter = createString("owWriter");

    public QOnewordEntity(String variable) {
        super(OnewordEntity.class, forVariable(variable));
    }

    public QOnewordEntity(Path<? extends OnewordEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOnewordEntity(PathMetadata metadata) {
        super(OnewordEntity.class, metadata);
    }

}

