package org.ict.atti_boot.pay.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.ict.atti_boot.pay.jpa.repository.PayRepository;
import org.ict.atti_boot.pay.model.dto.PayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PayService {

    @Autowired
    private PayRepository payRepository;

    public PayEntity savePayment(PayDto payDto) {
        PayEntity payEntity = payDto.toEntity();
        return payRepository.save(payEntity);
    }
}
