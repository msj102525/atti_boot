package org.ict.atti_boot.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.output.CustomUserDetails;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

//    @GetMapping
//    public ResponseEntity<User> getUser() {
//        User user = new User();
//        return ResponseEntity.ok(user);
//    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        // userId에 해당하는 사용자를 조회합니다.
        Optional<User> user = userService.findById(userId);
        log.info("user: {}", user);
        // 사용자가 존재하면 HTTP 200 OK와 함께 사용자 정보를 반환합니다.
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            // 사용자가 존재하지 않으면 HTTP 404 Not Found를 반환합니다.
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = false) User user) {
        log.info("userSignup: " + user);
        try {
            // 유효성 검사
            if (user == null || user.getUserName() == null || user.getUserName().isEmpty()) {
                return ResponseEntity.status(400).body("user_name 필드는 필수입니다.");
            }
            // 회원가입 처리
            User newUser = userService.signUpUser(user);
            log.info("New user: " + newUser);
            log.info("userName: {}", user.getUserName());

            return ResponseEntity.ok("회원가입이 완료되었습니다!");

        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage());
            return ResponseEntity.status(500).body("회원가입에 실패했습니다.");
        }
    }

//    @GetMapping("/mypage")
//    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
//        if (userDetails == null) {
//            log.warn("UserDetails is null, user not authenticated.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//        Optional<User> user = userService.findById(userDetails.getUsername());
//        log.info("getCurrentUser: " + user);
//        if (user.isPresent()) {
//            log.info("getCurrentUser: " + user.get());
//            return ResponseEntity.ok(user.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/current/{userId}")
    public Optional<User> getCurrentUser(@PathVariable String userId) {
        // Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Authentication 객체가 null인지와 인증이 되어 있는지를 먼저 확인합니다.
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        // Principal 객체가 CustomUserDetails 인스턴스인지 확인합니다.
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            return Optional.empty();
        }

        // CustomUserDetails 객체를 가져옵니다.
        CustomUserDetails userDetails = (CustomUserDetails) principal;

        // userService를 통해 사용자 정보를 가져옵니다.
        Optional<User> userOp = userService.findByUserId(userId);
        if (!userOp.isPresent()) {
            return Optional.empty();
        }
        User user = userOp.get();
        // 여기서 필요한 로직을 추가적으로 수행할 수 있습니다.
        // 예: 현재 사용자와 요청된 사용자가 동일한지 확인

        return Optional.of(user);
    }



    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails.getUsername().equals(user.getUserId())) {
            log.info("userUpdate: " + user);
            User updatedUser = userService.updateUser(user);
            log.info("updatedUser: " + updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("deleteUser1: " + userId);
        if (customUserDetails.getUsername().equals(userId)) {
            log.info("userDelete2: " + userId);
            userService.deleteUser(userId);
            log.info("userDelete3: " + userId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }



}