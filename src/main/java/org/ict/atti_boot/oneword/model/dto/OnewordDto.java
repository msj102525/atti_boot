package org.ict.atti_boot.oneword.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.oneword.jpa.entity.OnewordEntity;
import org.springframework.stereotype.Component;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class OnewordDto {
    private int owNum;
    private int owsjNum;
    private String owContent;
    private int owRcount;
    private String owWriter;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String owWriteDate;

    public OnewordEntity toEntity(){
        return OnewordEntity.builder()
                .owNum(owNum)
                .owsjNum(owsjNum)
                .owContent(owContent)
                .owRcount(owRcount)
                .owWriter(owWriter)
                .owWriteDate(owWriteDate != null ? java.sql.Date.valueOf(owWriteDate) : null)
                .build();
    }

}
