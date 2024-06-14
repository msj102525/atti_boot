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

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
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
