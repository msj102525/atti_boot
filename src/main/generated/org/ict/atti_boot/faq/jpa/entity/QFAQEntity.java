package org.ict.atti_boot.faq.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFAQEntity is a Querydsl query type for FAQEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFAQEntity extends EntityPathBase<FAQEntity> {

    private static final long serialVersionUID = -999678660L;

    public static final QFAQEntity fAQEntity = new QFAQEntity("fAQEntity");

    public final StringPath faqCategory = createString("faqCategory");

    public final StringPath faqContent = createString("faqContent");

    public final NumberPath<Integer> faqNum = createNumber("faqNum", Integer.class);

    public final StringPath faqTitle = createString("faqTitle");

    public final StringPath faqWriter = createString("faqWriter");

    public QFAQEntity(String variable) {
        super(FAQEntity.class, forVariable(variable));
    }

    public QFAQEntity(Path<? extends FAQEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFAQEntity(PathMetadata metadata) {
        super(FAQEntity.class, metadata);
    }

}

