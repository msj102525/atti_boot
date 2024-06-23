package org.ict.atti_boot.inquiry.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "INQUIRY")
@Entity
public class InquiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inquiry_seq_gen")
    @SequenceGenerator(name = "inquiry_seq_gen", sequenceName = "INQUIRY_SEQ", allocationSize = 1)
    @Column(name = "INQUIRY_NO", nullable = false)
    private int inquiryNo;

    @Column(name = "TITLE", nullable = false, length = 250)
    private String title;

    @Column(name = "CONTENT", nullable = false, length = 3000)
    private String content;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "INQUIRY_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date inquiryDate;

    @Column(name = "STATUS", nullable = false, length = 50)
    private String status;

    @PrePersist
    protected void onCreate() {
        inquiryDate = new Date();
        status = "미처리";
    }


}
