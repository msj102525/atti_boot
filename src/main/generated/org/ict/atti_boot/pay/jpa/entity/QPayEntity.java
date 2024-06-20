package org.ict.atti_boot.pay.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPayEntity is a Querydsl query type for PayEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayEntity extends EntityPathBase<PayEntity> {

    private static final long serialVersionUID = -613238020L;

    public static final QPayEntity payEntity = new QPayEntity("payEntity");

    public final NumberPath<Integer> payAmount = createNumber("payAmount", Integer.class);

    public final DateTimePath<java.util.Date> payDate = createDateTime("payDate", java.util.Date.class);

    public final StringPath payMethod = createString("payMethod");

    public final StringPath payNum = createString("payNum");

    public final StringPath userId = createString("userId");

    public QPayEntity(String variable) {
        super(PayEntity.class, forVariable(variable));
    }

    public QPayEntity(Path<? extends PayEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPayEntity(PathMetadata metadata) {
        super(PayEntity.class, metadata);
    }

}

