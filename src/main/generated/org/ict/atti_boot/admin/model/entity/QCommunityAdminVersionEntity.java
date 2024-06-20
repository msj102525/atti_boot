package org.ict.atti_boot.admin.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommunityAdminVersionEntity is a Querydsl query type for CommunityAdminVersionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityAdminVersionEntity extends EntityPathBase<CommunityAdminVersionEntity> {

    private static final long serialVersionUID = -98592303L;

    public static final QCommunityAdminVersionEntity communityAdminVersionEntity = new QCommunityAdminVersionEntity("communityAdminVersionEntity");

    public final StringPath feedContent = createString("feedContent");

    public final NumberPath<Long> feedNum = createNumber("feedNum", Long.class);

    public final StringPath userId = createString("userId");

    public QCommunityAdminVersionEntity(String variable) {
        super(CommunityAdminVersionEntity.class, forVariable(variable));
    }

    public QCommunityAdminVersionEntity(Path<? extends CommunityAdminVersionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommunityAdminVersionEntity(PathMetadata metadata) {
        super(CommunityAdminVersionEntity.class, metadata);
    }

}

