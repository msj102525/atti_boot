package org.ict.atti_boot.user.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "SOCIALLOGINS")
@Entity
public class SocialLogin {

    @Id
    @Column(name = "SOCIALUSERID")
    private String socialUserId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "SOCIALSITE")
    private String socialsite;

    @Column(name = "LOGIN_TIME")
    private LocalDateTime loginTime;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

}
