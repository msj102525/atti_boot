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
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="PAY")
@Entity
public class PayEntity {

    @Id
    @Column(name="PAY_NUM")
    private String payNum;

    @Column(name="USER_ID")
    private String userId;

    @Column(name = "pay_date")
    private LocalDateTime payDate;

    @Column(name="PAY_AMIOUNT")
    private int payAmount;

    @Column(name="PAY_METHOD")
    private String payMethod;


    public PayDto toDto() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return PayDto.builder()
                .payNum(payNum)
                .userId(userId)
                .payAmount(payAmount)
                .payMethod(payMethod)
                .payDate(payDate.format(formatter))
                .build();
    }


}
