package org.ict.atti_boot.likeHistory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.likeHistory.model.entity.LikeHistory;
import org.ict.atti_boot.likeHistory.service.LikeHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feed/like")
@Slf4j
@RequiredArgsConstructor
public class LikeHistoryController {
    private final LikeHistoryService likeHistoryService;

    @PostMapping("")
    public ResponseEntity<String> postLikeHistory(@RequestParam int feed) {
        log.info("feedNum : {}", feed);

        LikeHistory likeHistory = LikeHistory.builder()
                .userId("user01")
                .feedNum(feed)
                .build();

        return ResponseEntity.ok(feed+ "번 공감" + likeHistoryService.postLikeHistory(likeHistory));
    }

}
