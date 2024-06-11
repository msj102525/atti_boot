package org.ict.atti_boot.oneword.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.model.dto.OnewordDto;
import org.ict.atti_boot.oneword.model.service.OnewordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/oneword")
@RequiredArgsConstructor
@CrossOrigin     //// 리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class OnewordController {
    private final OnewordService onewordService;


    @PostMapping
    public ResponseEntity<Void> insertOneword(@RequestBody OnewordDto onewordDto){
        log.info("insertBoard : {}", onewordDto);
        onewordService.insertOneword(onewordDto);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{onewordNum}")  //요청 경로에 반드시 pk 에 해당하는 값을 전송해야 함 (안 보내면 에러)
    public ResponseEntity<Void> updateOneword(
            @PathVariable("onewordNum") int onewordNum,
            @RequestBody OnewordDto onewordDto){
        log.info("updateOnewordSubject : {}", onewordNum);
        onewordService.updateOneword(onewordDto);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{onewordNum}")
    public ResponseEntity<Void> deleteOneword(@PathVariable("onewordNum") int onewordNum){
        log.info("deleteNotice : {} ", onewordNum);
        onewordService.deleteOneword(onewordNum);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
