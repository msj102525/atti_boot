package org.ict.atti_boot.feed.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.feed.model.output.FeedSaveOutput;

import java.time.LocalDateTime;

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

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "FEED_CONTENT", columnDefinition = "VARCHAR2(1000)")
    private String feedContent;

    @Column(name = "FEED_DATE", columnDefinition = "DATE DEFAULT sysdate")
    private LocalDateTime feedDate;

    @Column(name = "FEED_READCOUNT", columnDefinition = "NUMBER DEFAULT 0")
    private int feedReadCount;

    @Column(name = "CATEGORY", columnDefinition = "VARCHAR2(20)")
    private String category;

    @Column(name = "IN_PUBLIC", columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private String inPublic;

    @PrePersist
    protected void onCreate() {
        this.feedDate = LocalDateTime.now();
    }

}
