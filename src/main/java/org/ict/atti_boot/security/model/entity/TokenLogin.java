package org.ict.atti_boot.security.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.user.jpa.entity.User;

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

    @Column(length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (accessCreated == null) accessCreated = now;
        if (refreshCreated == null) refreshCreated = now;
        if (accessExpires == null) accessExpires = now.plusSeconds(1500); // 1500초(25분) 설정
        if (refreshExpires == null) refreshExpires = now.plusSeconds(3600); // 3600초(1시간) 설정
//        if (accessExpires == null) accessExpires = now.plusSeconds(15); // 10초 설정
//        if (refreshExpires == null) refreshExpires = now.plusSeconds(60); // 30초 설정
    }
}
