package org.ict.atti_boot.feed.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.feed.model.entity.Feed;
import org.ict.atti_boot.feed.model.input.FeedSaveInputDto;
import org.ict.atti_boot.feed.model.output.FeedSaveOutput;
import org.ict.atti_boot.feed.repository.FeedContentVo;
import org.ict.atti_boot.feed.service.FeedService;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
@Slf4j
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @GetMapping("/top5")
    public ResponseEntity<?> selectTop5Feeds() {
        List<FeedContentVo> top5FeedContentList = feedService.selectTop5Feed();

        return ResponseEntity.ok().body(top5FeedContentList);
    }

    @PostMapping("")
    public ResponseEntity<FeedSaveOutput> insertFeed(@RequestBody FeedSaveInputDto feedSaveInputDto) {
        log.info(feedSaveInputDto.toString());

        Feed saveFeed = Feed.builder()
                .userId("JWT를 통해서 가져온 userId")
                .feedContent(feedSaveInputDto.getFeedContent())
                .category(feedSaveInputDto.getCategory())
                .inPublic(feedSaveInputDto.getIsPublic())
                .build();

        log.info(saveFeed.toString());


        Feed savedFeed = feedService.save(saveFeed);

        FeedSaveOutput resultFeed = FeedSaveOutput.builder()
                .feedNum(savedFeed.getFeedNum())
                .userId(savedFeed.getUserId())
                .feedContent(savedFeed.getFeedContent())
                .feedDate(savedFeed.getFeedDate())
                .feedReadCount(savedFeed.getFeedReadCount())
                .category(savedFeed.getCategory())
                .inPublic(savedFeed.getInPublic())
                .build();

        log.info(resultFeed.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(resultFeed);
    }
}
