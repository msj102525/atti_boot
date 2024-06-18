package org.ict.atti_boot.pay.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.springframework.stereotype.Component;

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

    public PayEntity toEntity(){
        return PayEntity.builder()
                .payNum(payNum)
                .userId(userId)
                .payAmount(payAmount)
                .payMethod(payMethod)
                .payDate(new java.sql.Date(System.currentTimeMillis())) // 현재 시스템 날짜로 설정
                .build();
    }

}
