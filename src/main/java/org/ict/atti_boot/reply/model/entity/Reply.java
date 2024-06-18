package org.ict.atti_boot.reply.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.feed.model.entity.Feed;
import org.ict.atti_boot.user.jpa.entity.User;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "REPLY")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLY_SEQ_GENERATOR")
    @SequenceGenerator(name = "REPLY_SEQ_GENERATOR", sequenceName = "REPLY_SEQ", allocationSize = 1)
    @Column(name = "REPLY_NUM")
    private int replyNum;

//    @Column(name = "USER_ID", nullable = false)
//    private String userId;

    @Column(name = "FEED_NUM", nullable = false)
    private int feedNum;

    @Column(name = "REPLY_CONTENT", columnDefinition = "VARCHAR2(50)")
    private String replyContent;

    @Column(name = "REPLY_DATE", columnDefinition = "DATE DEFAULT SYSDATE")
    private LocalDateTime replyDate;

    // 참조 답글 번호
    @Column(name = "REPLY_REPLY_REF", columnDefinition = "DEFAULT 0")
    private int replyReplyRef;

    // 답글 단계
    @Column(name = "REPLY_LEV", columnDefinition = "DEFAULT 1")
    private int replyLev;

    // 답글 순번
    @Column(name = "REPLY_SEQ", columnDefinition = "DEFAULT 1")
    private int replySeq;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @PrePersist
    protected void onCreate() {
        this.replyDate = LocalDateTime.now();
    }
}
