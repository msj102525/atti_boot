package org.ict.atti_boot.oneword.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.model.dto.OnewordSubjectDto;
import org.ict.atti_boot.oneword.model.service.OnewordSubjectService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/onewordsubject")
@RequiredArgsConstructor
@CrossOrigin     //// 리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class OnewordSubjectController {
    private final OnewordSubjectService onewordSubjectService;

    @GetMapping("/list")
    public ResponseEntity<List<OnewordSubjectDto>> selectList(@RequestParam(name="page", defaultValue = "0") int page,
                                                             @RequestParam(name="size", defaultValue = "10") int size) {
        log.info("/onewordsubject/list : {}", page + ",  " + size);
        //JPA 가 제공하는 Pageable 객체를 사용함
        //Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "owsjNum"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "owsjNum"));

        //response.put("totalItems", noticePageDto.getTotalElements());
        //response.put("totalPages", noticePageDto.getTotalPages());

        log.info("page 갯수 : {}", pageable.getPageNumber());

        //페이지에 출력할 목록 조회해 옴 => 응답 처리
        return new ResponseEntity<>(onewordSubjectService.selectList(pageable), HttpStatus.OK);
    }

    @GetMapping("/onesjdetail/{owsjNum}")
    public ResponseEntity<OnewordSubjectDto> selectOnewordSubjectDetail(@PathVariable("owsjNum") int owsjNum){
        //log.info("/boards/boardNum => selectBoardDetail()");
        log.info("onewordsubject/{} 요청", owsjNum);
        return new ResponseEntity<>(onewordSubjectService.selectOnewordSubjectDetail(owsjNum), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insertOnewordSubject(@RequestBody OnewordSubjectDto onewordSubjectDto){
        log.info("insertBoard : {}", onewordSubjectDto);
        onewordSubjectService.insertOnewordSubject(onewordSubjectDto);
        //return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Void>(HttpStatus.CREATED);  //// 글등록 성공시 생성되었다는 상태 코드를 반환함
    }

//    @PutMapping("/{owsjNum}")  //요청 경로에 반드시 pk 에 해당하는 값을 전송해야 함 (안 보내면 에러)
    @PutMapping  //요청 경로에 반드시 pk 에 해당하는 값을 전송해야 함 (안 보내면 에러)
    public ResponseEntity<OnewordSubjectDto> updateOnewordSubject(
//            @PathVariable("owsjNum") int owsjNum,
            @RequestBody OnewordSubjectDto onewordSubjectDto){
//        log.info("updateOnewordSubject : " + owsjNum);
        log.info("updateOnewordSubject : {} ", onewordSubjectDto);
        onewordSubjectService.updateOnewordSubject(onewordSubjectDto);
        //return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(onewordSubjectDto, HttpStatus.OK);
    }

    @DeleteMapping("/{owsjNum}")
    public ResponseEntity<Integer> deleteOnewordSubject(@PathVariable("owsjNum") int owsjNum){
        log.info("deleteNotice : {}", owsjNum);
        onewordSubjectService.deleteOnewordSubject(owsjNum);
        //return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(owsjNum, HttpStatus.OK);
    }

}
