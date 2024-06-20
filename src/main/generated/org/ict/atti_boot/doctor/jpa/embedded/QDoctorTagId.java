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

    public static final QDoctorTagId doctorTagId = new QDoctorTagId("doctorTagId");

    public final ListPath<org.ict.atti_boot.doctor.jpa.entity.Doctor, org.ict.atti_boot.doctor.jpa.entity.QDoctor> doctors = this.<org.ict.atti_boot.doctor.jpa.entity.Doctor, org.ict.atti_boot.doctor.jpa.entity.QDoctor>createList("doctors", org.ict.atti_boot.doctor.jpa.entity.Doctor.class, org.ict.atti_boot.doctor.jpa.entity.QDoctor.class, PathInits.DIRECT2);

    public final StringPath tag = createString("tag");

    public QDoctorTagId(String variable) {
        super(DoctorTagId.class, forVariable(variable));
    }

    public QDoctorTagId(Path<? extends DoctorTagId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDoctorTagId(PathMetadata metadata) {
        super(DoctorTagId.class, metadata);
    }

}

