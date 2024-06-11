package org.ict.atti_boot.doctor.jpa.embedded;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDoctorTagId is a Querydsl query type for DoctorTagId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDoctorTagId extends BeanPath<DoctorTagId> {

    private static final long serialVersionUID = -834162127L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDoctorTagId doctorTagId = new QDoctorTagId("doctorTagId");

    public final org.ict.atti_boot.doctor.jpa.entity.QDoctor doctor;

    public final StringPath tag = createString("tag");

    public QDoctorTagId(String variable) {
        this(DoctorTagId.class, forVariable(variable), INITS);
    }

    public QDoctorTagId(Path<? extends DoctorTagId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDoctorTagId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDoctorTagId(PathMetadata metadata, PathInits inits) {
        this(DoctorTagId.class, metadata, inits);
    }

    public QDoctorTagId(Class<? extends DoctorTagId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.doctor = inits.isInitialized("doctor") ? new org.ict.atti_boot.doctor.jpa.entity.QDoctor(forProperty("doctor"), inits.get("doctor")) : null;
    }

}

