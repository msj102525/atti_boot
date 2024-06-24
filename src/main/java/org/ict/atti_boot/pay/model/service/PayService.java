package org.ict.atti_boot.pay.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.ict.atti_boot.pay.jpa.repository.PayRepository;
import org.ict.atti_boot.pay.model.dto.PayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<PayDto> selectList(PageRequest pageRequest) {

        Page<PayEntity> pages = payRepository.findAll(pageRequest);
        return pages.map(PayEntity::toDto); // Page<BoardEntity>를 Page<BoardDto>로 변환
    }

   public Page<PayDto> searchByUserId(String userId, Pageable pageable) {
        Page<PayEntity> entities = payRepository.findByUserId(userId, pageable);
        return entities.map(PayDto::fromEntity);
    }

    public Page<PayDto> searchByDateRange(String beginDate, String endDate, Pageable pageable) {
        LocalDateTime start = LocalDateTime.parse(beginDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        Page<PayEntity> entities = payRepository.findByPayDateBetween(start, end, pageable);
        return entities.map(PayDto::fromEntity);
    }

    public Page<PayDto> searchByPayMethod(String payMethod, Pageable pageable) {
        Page<PayEntity> entities = payRepository.findByPayMethod(payMethod, pageable);
        return entities.map(PayDto::fromEntity);
    }

    public List<PayEntity> getAllPays() {
        return payRepository.findAll();
    }

}
