package org.ict.atti_boot.pay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.pay.model.dto.PayDto;
import org.ict.atti_boot.pay.model.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    try {
        payService.savePayment(payDto);
        return ResponseEntity.ok(1); // 성공 시 1 반환
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0); // 실패 시 0 반환
    }
}




}
