package org.ict.atti_boot.oneword.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.model.dto.OnewordSubjectDto;
import org.ict.atti_boot.oneword.model.service.OnewordSubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/onewordsubject")
@RequiredArgsConstructor
@CrossOrigin     //// 리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class OnewordSubjectController {
    private final OnewordSubjectService onewordSubjectService;

    @PostMapping
    public ResponseEntity<Void> insertOnewordSubject(@RequestBody OnewordSubjectDto onewordSubjectDto){
        log.info("insertBoard : {}", onewordSubjectDto);
        onewordSubjectService.insertOnewordSubject(onewordSubjectDto);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{onewordSubjectNum}")  //요청 경로에 반드시 pk 에 해당하는 값을 전송해야 함 (안 보내면 에러)
    public ResponseEntity<Void> updateOnewordSubject(
            @PathVariable("onewordSubjectNum") int onewordSubjectNum,
            @RequestBody OnewordSubjectDto onewordSubjectDto){
        log.info("updateOnewordSubject : " + onewordSubjectNum);
        onewordSubjectService.updateOnewordSubject(onewordSubjectDto);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{onewordSubjectNum}")
    public ResponseEntity<Void> deleteOnewordSubject(@PathVariable("onewordSubjectNum") int onewordSubjectNum){
        log.info("deleteNotice : {}", onewordSubjectNum);
        onewordSubjectService.deleteOnewordSubject(onewordSubjectNum);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
