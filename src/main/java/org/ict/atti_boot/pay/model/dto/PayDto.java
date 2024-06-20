package org.ict.atti_boot.pay.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class PayDto {

    private String payNum;
    private String userId;
    private String payDate;
    private int payAmount;
    private String payMethod;


    public PayEntity toEntity() {
        Instant instant = Instant.parse(payDate);
        LocalDateTime parsedPayDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return PayEntity.builder()
                .payNum(payNum)
                .userId(userId)
                .payAmount(payAmount)
                .payMethod(payMethod)
                .payDate(parsedPayDate)
                .build();
    }


}
