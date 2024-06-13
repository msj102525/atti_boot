package org.ict.atti_boot.likeHistory.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.user.jpa.entity.User;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "LIKE_HISTORY")
public class LikeHistory {
    @Id
    @Column(name = "LIKE_HISTORY_ID")
    private int likeHistoryId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "FEED_NUM", nullable = false)
    private int feedNum;
}
