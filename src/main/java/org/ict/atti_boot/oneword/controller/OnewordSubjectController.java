package org.ict.atti_boot.oneword.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.model.dto.OnewordSubjectDto;
import org.ict.atti_boot.oneword.model.service.OnewordSubjectService;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
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

    private final JWTUtil jwtUtil;
    //private final UserRepository userRepository;

    @GetMapping("/list")
    public ResponseEntity<List<OnewordSubjectDto>> selectList(
            @RequestParam(name="keyword", defaultValue = "") String keyword,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "10") int size) {

        log.info("/onewordsubject/list : {}", page + ",  " + size);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "owsjNum"));
        //Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "owsjNum"));

        //response.put("totalItems", noticePageDto.getTotalElements());
        //response.put("totalPages", noticePageDto.getTotalPages());

        log.info("/onewordsubject/list page 갯수 : {}", pageable.getPageNumber());

        if (keyword.isEmpty()) {
            //페이지에 출력할 목록 조회해 옴 => 응답 처리
            return new ResponseEntity<>(onewordSubjectService.selectList(pageable), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(onewordSubjectService.selectSearchOwsjSubject(keyword, pageable), HttpStatus.OK);
        }
    }

    // 총 건수
    @GetMapping("/list/count")
    public ResponseEntity<Long> getCountOwsjSubjectList(
            @RequestParam(name="keyword", defaultValue = "") String keyword) {

        long count;
        //페이지에 출력할 목록 조회해 옴 => 응답 처리
        if (keyword.isEmpty()) {
            count = onewordSubjectService.getCountOwsjSubjectList();
        } else {
            count = onewordSubjectService.getCountSearchOwsjSubject(keyword);
        }
        return ResponseEntity.ok(count);
    }

    @GetMapping("/onesjdetail/{owsjNum}")
    public ResponseEntity<OnewordSubjectDto> selectOnewordSubjectDetail(@PathVariable("owsjNum") int owsjNum){
        //log.info("/boards/boardNum => selectBoardDetail()");
        log.info("onewordsubject/{} 요청", owsjNum);
        return new ResponseEntity<>(onewordSubjectService.selectOnewordSubjectDetail(owsjNum), HttpStatus.OK);
    }

    @GetMapping("/searchowsjsubject")
    public ResponseEntity<List<OnewordSubjectDto>> selectSearchOwsjSubject(
            @RequestParam(name="keyword") String keyword,
            @RequestParam(name="page") int page,
            @RequestParam(name="size") int size) {
        log.info("/onewordsubject/searchowsjsubject : {} ", keyword + ", " + page + ",  " + size);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "owsjNum"));

        //페이지에 출력할 목록 조회해 옴 => 응답 처리
        return new ResponseEntity<>(onewordSubjectService.selectSearchOwsjSubject(keyword, pageable), HttpStatus.OK);
    }

    //// 제목 검색 목록 건수
    @GetMapping("/searchowsjsubject/count")
    public ResponseEntity<Long> getCountSearchOwsjSubject(@RequestParam(name="keyword") String keyword) {
        long count = onewordSubjectService.getCountSearchOwsjSubject(keyword);
        return ResponseEntity.ok(count);
    }

    @PostMapping // insert
    public ResponseEntity<?> insertOnewordSubject(HttpServletRequest request,
                                                  @RequestBody OnewordSubjectDto onewordSubjectDto){
        log.info("insertBoard : {}", onewordSubjectDto);

        //// 2024.06.25
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        String userEmail = jwtUtil.getUserEmailFromToken(token);
        boolean isAdmin = jwtUtil.isAdminFromToken(token);

        log.info("insertBoard(token-2024.06.25) : {}", token);

//        if (!isAdmin) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. User is not an administrator.");
//        }

        onewordSubjectService.insertOnewordSubject(onewordSubjectDto);
        //return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Void>(HttpStatus.CREATED);  //// 글등록 성공시 생성되었다는 상태 코드를 반환함
    }

    @PutMapping("/{owsjNum}")  //요청 경로에 반드시 pk 에 해당하는 값을 전송해야 함 (안 보내면 에러)(update)
    public ResponseEntity<OnewordSubjectDto> updateOnewordSubject(
            @PathVariable("owsjNum") int owsjNum,
            @RequestBody OnewordSubjectDto onewordSubjectDto){
        log.info("updateOnewordSubject : {}, {}", owsjNum, onewordSubjectDto);
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
