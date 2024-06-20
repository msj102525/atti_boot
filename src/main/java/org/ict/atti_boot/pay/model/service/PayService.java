package org.ict.atti_boot.pay.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.ict.atti_boot.pay.jpa.repository.PayRepository;
import org.ict.atti_boot.pay.model.dto.PayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PayService {

    @Autowired
    private PayRepository payRepository;

    public int savePayment(PayDto payDto) {
        PayEntity paymentEntity = payDto.toEntity();
        payRepository.save(paymentEntity);
        log.info(payRepository.save(paymentEntity) + "asdasdasd");

        if (payRepository.save(paymentEntity).toString().length() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public List<PayEntity> getRecentPayments(String userId) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);
        return payRepository.findRecentPayments(userId, startTime, endTime);
    }


}
