package org.ict.atti_boot.review.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 803159933L;

    public static final QReview review = new QReview("review");

    public final StringPath content = createString("content");

    public final StringPath doctorId = createString("doctorId");

    public final StringPath reviewId = createString("reviewId");

    public final NumberPath<Integer> startPoint = createNumber("startPoint", Integer.class);

    public final StringPath userId = createString("userId");

    public final DatePath<java.sql.Date> writeDate = createDate("writeDate", java.sql.Date.class);

    public QReview(String variable) {
        super(Review.class, forVariable(variable));
    }

    public QReview(Path<? extends Review> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReview(PathMetadata metadata) {
        super(Review.class, metadata);
    }

}

