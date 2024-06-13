package org.ict.atti_boot.feed.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.likeHistory.model.entity.LikeHistory;
import org.ict.atti_boot.reply.model.entity.Reply;
import org.ict.atti_boot.user.jpa.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "FEED")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEED_SEQ_GENERATOR")
    @SequenceGenerator(name = "FEED_SEQ_GENERATOR", sequenceName = "FEED_SEQ", allocationSize = 1)
    @Column(name = "FEED_NUM")
    private int feedNum;

//    @Column(name = "USER_ID", nullable = false)
//    private String userId;

    @Column(name = "FEED_CONTENT", columnDefinition = "VARCHAR2(1000)")
    private String feedContent;

    @Column(name = "FEED_DATE", columnDefinition = "DATE DEFAULT SYSDATE")
    private LocalDateTime feedDate;

    @Column(name = "FEED_READCOUNT", columnDefinition = "NUMBER DEFAULT 0")
    private int feedReadCount;

    @Column(name = "CATEGORY", columnDefinition = "VARCHAR2(20)")
    private String category;

    @Column(name = "IN_PUBLIC", columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private String inPublic;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany
    @JoinColumn(name = "FEED_NUM")
    private List<Reply> replies;

    @OneToMany
    @JoinColumn(name = "FEED_NUM")
    private List<LikeHistory> likeHistories;

    @PrePersist
    protected void onCreate() {
        this.feedDate = LocalDateTime.now();
    }

}
