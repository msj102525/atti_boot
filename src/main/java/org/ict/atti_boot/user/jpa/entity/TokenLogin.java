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
@Table(name = "TOKEN_LOGIN")
@Entity
public class TokenLogin {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ACCESSTOKEN")
    private String accessToken;

    @Column(name = "REFRESHTOKEN")
    private String refreshToken;

    @Column(name = "ACCESS_CREATED")
    private LocalDateTime accessCreated;

    @Column(name = "ACCESS_EXPIRES")
    private LocalDateTime accessExpires;

    @Column(name = "REFRESH_EXPIRES")
    private LocalDateTime refreshExpires;

    @Column(name = "REFRESH_CREATED")
    private LocalDateTime refreshCreated;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_ID", referencedColumnName = "user_id")
    private User user;

}
