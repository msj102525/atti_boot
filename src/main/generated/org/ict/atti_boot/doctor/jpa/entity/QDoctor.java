package org.ict.atti_boot.doctor.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDoctor is a Querydsl query type for Doctor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDoctor extends EntityPathBase<Doctor> {

    private static final long serialVersionUID = -855756981L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDoctor doctor = new QDoctor("doctor");

    public final StringPath hospitalAddress = createString("hospitalAddress");

    public final StringPath hospitalName = createString("hospitalName");

    public final StringPath hospitalPhone = createString("hospitalPhone");

    public final StringPath introduce = createString("introduce");

    public final ListPath<DoctorTag, QDoctorTag> tags = this.<DoctorTag, QDoctorTag>createList("tags", DoctorTag.class, QDoctorTag.class, PathInits.DIRECT2);

    public final org.ict.atti_boot.user.jpa.entity.QUser user;

    public final StringPath userId = createString("userId");

    public QDoctor(String variable) {
        this(Doctor.class, forVariable(variable), INITS);
    }

    public QDoctor(Path<? extends Doctor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDoctor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDoctor(PathMetadata metadata, PathInits inits) {
        this(Doctor.class, metadata, inits);
    }

    public QDoctor(Class<? extends Doctor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new org.ict.atti_boot.user.jpa.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

