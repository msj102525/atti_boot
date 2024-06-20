package org.ict.atti_boot.doctor.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDoctorTag is a Querydsl query type for DoctorTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDoctorTag extends EntityPathBase<DoctorTag> {

    private static final long serialVersionUID = 1069731919L;

    public static final QDoctorTag doctorTag = new QDoctorTag("doctorTag");

    public final StringPath tag = createString("tag");

    public final StringPath userId = createString("userId");

    public QDoctorTag(String variable) {
        super(DoctorTag.class, forVariable(variable));
    }

    public QDoctorTag(Path<? extends DoctorTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDoctorTag(PathMetadata metadata) {
        super(DoctorTag.class, metadata);
    }

}

