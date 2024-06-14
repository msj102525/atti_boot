package org.ict.atti_boot.faq.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.faq.jpa.entity.FAQEntity;
import org.springframework.stereotype.Component;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class FAQDto {

    private int faqNum;
    private String faqTitle;
    private String faqWriter;
    private String faqContent;
    private String faqCategory;

    public FAQEntity toEntity(){
        return FAQEntity.builder()
                .faqNum(faqNum)
                .faqTitle(faqTitle)
                .faqWriter(faqWriter)
                .faqContent(faqContent)
                .faqCategory(faqCategory)
                .build();
    }

}
