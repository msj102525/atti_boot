package org.ict.atti_boot.doctor.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDoctorTag is a Querydsl query type for DoctorTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDoctorTag extends EntityPathBase<DoctorTag> {

    private static final long serialVersionUID = 1069731919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDoctorTag doctorTag = new QDoctorTag("doctorTag");

    public final org.ict.atti_boot.doctor.jpa.embedded.QDoctorTagId id;

    public QDoctorTag(String variable) {
        this(DoctorTag.class, forVariable(variable), INITS);
    }

    public QDoctorTag(Path<? extends DoctorTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDoctorTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDoctorTag(PathMetadata metadata, PathInits inits) {
        this(DoctorTag.class, metadata, inits);
    }

    public QDoctorTag(Class<? extends DoctorTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new org.ict.atti_boot.doctor.jpa.embedded.QDoctorTagId(forProperty("id"), inits.get("id")) : null;
    }

}

