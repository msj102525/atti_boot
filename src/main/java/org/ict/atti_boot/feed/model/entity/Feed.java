package org.ict.atti_boot.feed.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "FEED")
public class Feed {

    @Id
    @Column(name = "FEED_NUM")
    private int feedNum;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "FEED_CONTENT", columnDefinition = "VARCHAR2(1000)")
    private String feedContent;

    @Column(name = "FEED_DATE")
    private LocalDateTime feedDate;

    @Column(name = "FEED_READCOUNT")
    private int feedReadCount;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "IN_PUBLIC")
    private String inPublic;
}
