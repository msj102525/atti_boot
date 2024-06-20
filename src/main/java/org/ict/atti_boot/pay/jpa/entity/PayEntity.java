package org.ict.atti_boot.pay.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
