package org.ict.atti_boot.oneword.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.oneword.jpa.entity.OnewordSubjectEntity;
import org.springframework.stereotype.Component;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class OnewordSubjectDto {
    private int owsjNum;
    private String owsjSubject;
    private String owsjWriter;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String owsjWriteDate;

    public OnewordSubjectEntity toEntity(){
        return OnewordSubjectEntity.builder()
                .owsjNum(owsjNum)
                .owsjSubject(owsjSubject)
                .owsjWriter(owsjWriter)
                .owsjWriteDate(owsjWriteDate != null ? java.sql.Date.valueOf(owsjWriteDate) : null)
                .build();
    }
}
