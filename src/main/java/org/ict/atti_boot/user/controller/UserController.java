package org.ict.atti_boot.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

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
    public ResponseEntity<User> getUser(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
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

    //아이디 찾기
    @PostMapping("/userIdfind")
    public ResponseEntity<String> findUsername(@RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        String email = request.get("email");
        log.info("Find username for userName: {}, email: {}", userName, email);

        Optional<User> user = userService.findByUserNameAndEmail(userName, email);
        if (user.isPresent()) {
            String userId = user.get().getUserId();
            log.info("Found user: {}", userId);

            // 이메일로 사용자 아이디 전송
            String subject = "Your User ID";
            String text = "Your user ID is: " + userId;
            log.info("Subject: {}", subject);
            log.info("Text: {}", text);
            userService.sendSimpleMessage(email, subject, text);

            return ResponseEntity.ok("사용자 아이디가 이메일로 전송되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }
    // 비밀번호 찾기
    @PostMapping("/passwordReset")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        String email = request.get("email");
        log.info("Reset password request for userName: {}, email: {}", userName, email);

        Optional<User> user = userService.findByUserNameAndEmail(userName, email);
        if (user.isPresent()) {
            // 임시 비밀번호 생성
            String temporaryPassword = generateTemporaryPassword();
            log.info("Generated temporary password: {}", temporaryPassword);

            // 사용자 비밀번호 업데이트
            try {
                userService.updateUserPassword(user.get().getUserId(), temporaryPassword);
            } catch (Exception e) {
                log.error("Failed to update user password: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 업데이트에 실패했습니다.");
            }

            // 이메일로 임시 비밀번호 전송
            String subject = "ATTI 임시 비밀번호";
            String text = "임시비밀번호는 : " + temporaryPassword + "입니다.";
            log.info("임시비밀번호: {}", text);
            try {
                userService.sendSimpleMessage(email, subject, text);
            } catch (Exception e) {
                log.error("Failed to send email: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송에 실패했습니다.");
            }

            return ResponseEntity.ok("임시 비밀번호가 이메일로 전송되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    private String generateTemporaryPassword() {
        int length = 8;
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder tempPassword = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charSet.length());
            tempPassword.append(charSet.charAt(index));
        }

        return tempPassword.toString();
    }





}
