package org.ict.atti_boot.user.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1209424995L;

    public static final QUser user = new QUser("user");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath email = createString("email");

    public final ComparablePath<Character> gender = createComparable("gender", Character.class);

    public final StringPath loginType = createString("loginType");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath profileUrl = createString("profileUrl");

    public final SetPath<org.ict.atti_boot.security.model.entity.RefreshToken, org.ict.atti_boot.security.model.entity.QRefreshToken> refreshToken = this.<org.ict.atti_boot.security.model.entity.RefreshToken, org.ict.atti_boot.security.model.entity.QRefreshToken>createSet("refreshToken", org.ict.atti_boot.security.model.entity.RefreshToken.class, org.ict.atti_boot.security.model.entity.QRefreshToken.class, PathInits.DIRECT2);

    public final StringPath snsAccessToken = createString("snsAccessToken");

    public final SetPath<SocialLogin, QSocialLogin> socialLogin = this.<SocialLogin, QSocialLogin>createSet("socialLogin", SocialLogin.class, QSocialLogin.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public final ComparablePath<Character> userType = createComparable("userType", Character.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

