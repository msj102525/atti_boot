package org.ict.atti_boot.pay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data  // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class PayDto {

    private String payNum;
    private String userId;
    private String payDate;  // 2024-06-20 09:56:13.354 형식의 문자열
    private int payAmount;
    private String payMethod;
    private String toDoctor;


    public PayEntity toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss.SSS");
        LocalDateTime parsedPayDate = LocalDateTime.parse(payDate, formatter);

        return PayEntity.builder()
                .payNum(payNum)
                .userId(userId)
                .payAmount(payAmount)
                .payMethod(payMethod)
                .toDoctor(toDoctor)
                .payDate(parsedPayDate)
                .build();
    }

    public static PayDto fromEntity(PayEntity payEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return PayDto.builder()
                .payNum(payEntity.getPayNum())
                .userId(payEntity.getUserId())
                .payDate(payEntity.getPayDate().format(formatter))
                .payAmount(payEntity.getPayAmount())
                .payMethod(payEntity.getPayMethod())
                .toDoctor(payEntity.getToDoctor())
                .build();
    }


}
