package org.ict.atti_boot.doctor.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCareer is a Querydsl query type for Career
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCareer extends EntityPathBase<Career> {

    private static final long serialVersionUID = -896883286L;

    public static final QCareer career1 = new QCareer("career1");

    public final StringPath career = createString("career");

    public final StringPath userId = createString("userId");

    public QCareer(String variable) {
        super(Career.class, forVariable(variable));
    }

    public QCareer(Path<? extends Career> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCareer(PathMetadata metadata) {
        super(Career.class, metadata);
    }

}

