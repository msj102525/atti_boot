package org.ict.atti_boot.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
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

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getUsername().equals(user.getUserId())) {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getUsername().equals(userId)) {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }


}
