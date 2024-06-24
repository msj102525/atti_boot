package org.ict.atti_boot.oneword.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.model.dto.OnewordDto;
import org.ict.atti_boot.oneword.model.dto.OnewordSubjectDto;
import org.ict.atti_boot.oneword.model.service.OnewordService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/oneword")
@RequiredArgsConstructor
@CrossOrigin     //// 리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class OnewordController {
    private final OnewordService onewordService;

    @GetMapping("/list")
    public ResponseEntity<List<OnewordDto>> selectList(@RequestParam(name="page", defaultValue = "0") int page,
                                                       @RequestParam(name="size", defaultValue = "10") int size) {
        log.info("/oneword/list : {}", page + ",  " + size);
        //JPA 가 제공하는 Pageable 객체를 사용함
        //Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "owsjNum"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "owNum"));

        //페이지에 출력할 목록 조회해 옴 => 응답 처리
        return new ResponseEntity<>(onewordService.selectList(pageable), HttpStatus.OK);
    }

    @GetMapping("/searchoneword")
    public ResponseEntity<List<OnewordDto>> selectSearchOneword(
            @RequestParam(name="keyword", defaultValue = "0") String keyword,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "10") int size) {
        log.info("/oneword/searchoneword : {} ", keyword + ", " + page + ",  " + size);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "owsjNum"));

        //페이지에 출력할 목록 조회해 옴 => 응답 처리
        return new ResponseEntity<>(onewordService.selectSearchOneword(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/onedetail/{owNum}")
    public ResponseEntity<OnewordDto> selectOnewordDetail(@PathVariable("owNum") int owNum){
        //log.info("/boards/boardNum => selectBoardDetail()");
        log.info("oneword/{} 요청", owNum);
        return new ResponseEntity<>(onewordService.selectOnewordDetail(owNum), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insertOneword(@RequestBody OnewordDto onewordDto){
        log.info("insertBoard : {}", onewordDto);
        onewordService.insertOneword(onewordDto);
        //return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Void>(HttpStatus.CREATED);  //// 글등록 성공시 생성되었다는 상태 코드를 반환함
    }

    @PutMapping("/{onewordNum}")  //요청 경로에 반드시 pk 에 해당하는 값을 전송해야 함 (안 보내면 에러)
    public ResponseEntity<OnewordDto> updateOneword(
            @PathVariable("onewordNum") int onewordNum,
            @RequestBody OnewordDto onewordDto){
        log.info("updateOnewordSubject : {}", onewordNum);
        onewordService.updateOneword(onewordDto);
        //return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(onewordDto, HttpStatus.OK);
    }

    @DeleteMapping("/{onewordNum}")
    public ResponseEntity<Integer> deleteOneword(@PathVariable("onewordNum") int onewordNum){
        log.info("deleteNotice : {} ", onewordNum);
        onewordService.deleteOneword(onewordNum);
        //return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); /// return 값 없으면, axios에서 오류 발생
        return new ResponseEntity<>(onewordNum, HttpStatus.OK);
    }

}
