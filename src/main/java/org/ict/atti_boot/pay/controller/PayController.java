package org.ict.atti_boot.pay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.ict.atti_boot.pay.model.dto.PayDto;
import org.ict.atti_boot.pay.model.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
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



}
