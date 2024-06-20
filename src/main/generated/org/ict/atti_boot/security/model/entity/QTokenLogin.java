package org.ict.atti_boot.security.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTokenLogin is a Querydsl query type for TokenLogin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTokenLogin extends EntityPathBase<TokenLogin> {

    private static final long serialVersionUID = -6985141L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTokenLogin tokenLogin = new QTokenLogin("tokenLogin");

    public final DateTimePath<java.time.LocalDateTime> accessCreated = createDateTime("accessCreated", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> accessExpires = createDateTime("accessExpires", java.time.LocalDateTime.class);

    public final StringPath accessToken = createString("accessToken");

    public final DateTimePath<java.time.LocalDateTime> refreshCreated = createDateTime("refreshCreated", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> refreshExpires = createDateTime("refreshExpires", java.time.LocalDateTime.class);

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath status = createString("status");

    public final org.ict.atti_boot.user.jpa.entity.QUser user;

    public final StringPath userId = createString("userId");

    public QTokenLogin(String variable) {
        this(TokenLogin.class, forVariable(variable), INITS);
    }

    public QTokenLogin(Path<? extends TokenLogin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTokenLogin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTokenLogin(PathMetadata metadata, PathInits inits) {
        this(TokenLogin.class, metadata, inits);
    }

    public QTokenLogin(Class<? extends TokenLogin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new org.ict.atti_boot.user.jpa.entity.QUser(forProperty("user")) : null;
    }

}

