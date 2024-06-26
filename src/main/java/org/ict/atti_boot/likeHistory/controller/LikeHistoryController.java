package org.ict.atti_boot.likeHistory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.likeHistory.model.entity.LikeHistory;
import org.ict.atti_boot.likeHistory.service.LikeHistoryService;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@Slf4j
@RequiredArgsConstructor
public class LikeHistoryController {
    private final LikeHistoryService likeHistoryService;
    private final JWTUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<String> postLikeHistory(@RequestHeader("Authorization") String token, @RequestParam int feed) {
        log.info("feedNum : {}", feed);

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 유저가 아님");
        }

        String userId = jwtUtil.getUserIdFromToken(token);

        LikeHistory likeHistory = LikeHistory.builder()
                .userId(userId)
                .feedNum(feed)
                .build();

        return ResponseEntity.ok(feed+ "번 공감" + likeHistoryService.postLikeHistory(likeHistory));
    }

}
