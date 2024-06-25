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

   public Page<PayEntity> getPays(String action, String keyword, LocalDateTime beginDate, LocalDateTime endDate, Pageable pageable) {
        if ("userId".equals(action)) {
            return payRepository.findAllByUserId(keyword, pageable);
        } else if ("payMethod".equals(action)) {
            return payRepository.findAllByPayMethod(keyword, pageable);
        } else if ("date".equals(action)) {
            return payRepository.findAllByPayDateBetween(beginDate, endDate, pageable);
        } else if (action == null){
            Page<PayEntity> pages = payRepository.findAll(pageable);
            return pages; //
        }

        else {
            throw new IllegalArgumentException("Invalid search action: " + action);
        }
    }

    public List<PayEntity> getAllPays() {
        return payRepository.findAll();
    }

}
