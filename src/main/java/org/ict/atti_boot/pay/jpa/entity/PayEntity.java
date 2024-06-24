package org.ict.atti_boot.pay.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.pay.model.dto.PayDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pay")
public class PayEntity {

    @Id
    private String payNum;
    private String userId;
    private int payAmount;
    private String payMethod;

    @Column(name = "pay_date")
    private LocalDateTime payDate;

    @Column(name = "todoctor")
    private String toDoctor;

    // 기타 필요한 필드 및 메서드

    public PayDto toDto() {

        return PayDto.builder()
                .payNum(payNum)
                .userId(userId)
                .payAmount(payAmount)
                .payMethod(payMethod)
                .toDoctor(toDoctor)
                .payDate(payDate.toString())
                .build();
    }
}
