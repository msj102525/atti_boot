package org.ict.atti_boot.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.output.CustomUserDetails;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    // 유저정보
    @GetMapping()
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

    //유저 정보 수정
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user, @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Authenticated user: {}", userDetails.getUsername());
        log.info("Request user ID: {}", user.getUserId());
        log.info("Request user data: {}", user);

        if (userDetails.getUsername().equals(user.getEmail())) {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            log.warn("Forbidden request: authenticated user {} does not match request user ID {}", userDetails.getUsername(), user.getUserId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // 유저 정보 삭제
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Authenticated deleteUser: {}", userDetails.getUserId());
        if (userDetails.getUserId().equals(userId)) {
            log.info("Request user ID: {}", userId);
            try {
                userService.deleteUser(userId);
                log.info("Deleted user: {}", userId);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                log.error("Error deleting user: {}", userId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            log.warn("Unauthorized delete attempt by user: {}", userDetails.getUserId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }
    }
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable String userId, @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request, HttpServletResponse response) {
//        log.info("Authenticated deleteUser: {}", userDetails.getUserId());
//        if (userDetails.getUserId().equals(userId)) {
//            log.info("Request user ID: {}", userId);
//            try {
//                // 로그아웃 처리
//                new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//
//                // 유저 삭제 처리
//                userService.deleteUser(userId);
//                log.info("Deleted user: {}", userId);
//                return ResponseEntity.noContent().build();
//            } catch (Exception e) {
//                log.error("Error deleting user: {}", userId, e);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            }
//        } else {
//            log.warn("Unauthorized delete attempt by user: {}", userDetails.getUserId());
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }


}
