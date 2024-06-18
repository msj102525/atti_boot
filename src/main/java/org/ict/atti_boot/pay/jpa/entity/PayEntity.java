package org.ict.atti_boot.pay.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.pay.model.dto.PayDto;

import java.util.Date;

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

    @Column(name="PAY_DATE")
    private Date payDate;

    @Column(name="PAY_AMIOUNT")
    private int payAmount;

    @Column(name="PAY_METHOD")
    private String payMethod;

    @PrePersist     //// jpa 로 넘어가기 전에 작동하라는 어노테이션임
    public void prePersist(){
        payDate = new Date(System.currentTimeMillis());   /// 현재 날짜, 시간 적용
    }

    public PayDto toDto(){
        return PayDto.builder()
                .payNum(payNum)
                .userId(userId)
                .payDate(payDate.toString())
                .payAmount(payAmount)
                .payMethod(payMethod)
                .build();
    }





}
