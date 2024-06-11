package org.ict.atti_boot.user.controller;


import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.input.InputUser;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";}

    @PostMapping("/user")
    public ResponseEntity<?> signUpUser(@RequestBody User user) {

        User newUser = userService.signUpUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = false) User user) {
        log.info("user: {}!!!!!!!!!!!!!!!!!!!!", user);

        // 유효성 검사 추가
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            return ResponseEntity.status(400).body("user_name 필드는 필수입니다.");
        }
        try {
            userService.signUpUser(user);
            log.info("userName: {}", user.getUserName());
            return ResponseEntity.ok("회원가입이 완료되었습니다!");

        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage());
            return ResponseEntity.status(500).body("회원가입에 실패했습니다.");
        }
    }




}
