package org.ict.atti_boot.security.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.user.jpa.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Data
@Table(name = "refresh_tokens")
public class RefreshToken {


    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name = "token_value", nullable = false, length = 255)
    private String tokenValue;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_in", nullable = false)
    private Long expiresIn;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(length = 50)
    private String status;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (issuedAt == null) issuedAt = now;
        if (expirationDate == null) expirationDate = now.plusSeconds(expiresIn / 1000); // 예를 들어 expiresIn이 밀리초 단위라면
    }
}
