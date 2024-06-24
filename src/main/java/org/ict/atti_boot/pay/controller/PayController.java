package org.ict.atti_boot.pay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.ict.atti_boot.pay.model.dto.PayDto;
import org.ict.atti_boot.pay.model.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
@CrossOrigin
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/save")
    public ResponseEntity<Integer> savePayment(@RequestBody PayDto payDto) {
        log.info(payDto + "zxczxc");
        int result = payService.savePayment(payDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PayEntity>> getRecentPayments(@RequestParam("userId") String userId) {
        List<PayEntity> payments = payService.getRecentPayments(userId);
        return ResponseEntity.ok(payments);
    }


    @GetMapping
    public Page<PayDto> getPayList(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return payService.selectList(pageRequest);
    }

    // 검색 처리
    @GetMapping("/search")
    public Page<PayDto> searchPays(
            @RequestParam String action,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String beginDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        log.info(pageable.getPageSize() + "페이지 사이즈");

        Page<PayDto> result;
        switch (action) {
            case "userId":
                result = payService.searchByUserId(keyword, pageable);
                break;
            case "date":
                result = payService.searchByDateRange(beginDate, endDate, pageable);
                break;
            case "payMethod":
                result = payService.searchByPayMethod(keyword, pageable);
                break;
            default:
                throw new IllegalArgumentException("Invalid search action");
        }

        return result;
    }

    @GetMapping("/graph")
    public List<PayEntity> getAllPayData() {
        return payService.getAllPays();
    }




}
