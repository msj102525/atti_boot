package org.ict.atti_boot.faq.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.faq.model.dto.FAQDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="FAQ")
@Entity
public class FAQEntity {

    @Id
    @SequenceGenerator(name = "faq_seq_gen", sequenceName = "FAQ_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_seq_gen")  //primary key 지정하는 어노테이션(자동 채번)
    @Column(name="FAQ_NUM")
    private int faqNum;

    @Column(name="FAQ_TITLE", nullable = true)
    private String faqTitle;

    @Column(name="FAQ_WRITER", nullable = true)
    private String faqWriter;

    @Column(name="FAQ_CONTENT", nullable = true)
    private String faqContent;

    @Column(name="FAQ_CATEGORY", nullable = true)
    private String faqCategory;



    public FAQDto toDto(){
        return FAQDto.builder()
                .faqNum(faqNum)
                .faqTitle(faqTitle)
                .faqWriter(faqWriter)
                .faqContent(faqContent)
                .faqCategory(faqCategory)
                .build();
    }


}
