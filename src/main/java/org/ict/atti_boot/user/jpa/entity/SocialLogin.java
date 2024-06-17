package org.ict.atti_boot.user.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "SOCIALLOGINS")
@Entity
public class SocialLogin {

    @Id
    @Column(name = "SOCIAL_USER_ID")
    private String socialUserId;        //소셜 아이디 ==이메일

    @Column(name = "USER_ID")
    private String userId;          // 유저 아이디

    @Column(name = "SOCIAL_SITE")
    private String socialsite;      //플랫폼 아이디

    @Column(name = "LOGIN_TIME")
    private LocalDateTime loginTime;    //로그인한 시간

    @Column(name = "SNS_ACCESS_TOKEN", nullable = true)
    private String snsAccessToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;


}
