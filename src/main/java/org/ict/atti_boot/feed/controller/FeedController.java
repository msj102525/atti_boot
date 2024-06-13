package org.ict.atti_boot.feed.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.feed.model.entity.Feed;
import org.ict.atti_boot.reply.model.entity.Reply;
import org.ict.atti_boot.feed.model.input.FeedSaveInputDto;
import org.ict.atti_boot.feed.model.output.FeedSaveOutput;
import org.ict.atti_boot.feed.repository.FeedContentVo;
import org.ict.atti_boot.feed.service.FeedService;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feed")
@Slf4j
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @GetMapping("/top5")
    public ResponseEntity<?> selectTop5Feeds() {
        List<FeedContentVo> top5FeedContentList = feedService.selectTop5Feed();

        return ResponseEntity.ok().body(top5FeedContentList);
    }

    @GetMapping("")
    public ResponseEntity<?> selectAllFeeds(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        log.info("selectAllFeeds called : {}, {}, {}", page, size, category);

        Sort sort = Sort.by(Sort.Direction.DESC, "feedDate");

        log.info("selectAllFeeds called : ", page, size, category);

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Feed> feeds = feedService.selectAllFeeds(pageable, category);

        feeds.getContent().forEach(feed -> {
            feed.getReplies().sort(Comparator.comparing(Reply::getReplyDate).reversed());
        });

        return ResponseEntity.ok().body(feeds);
    }

    @PostMapping("")
    public ResponseEntity<FeedSaveOutput> insertFeed(@RequestBody FeedSaveInputDto feedSaveInputDto) {
        log.info(feedSaveInputDto.toString());

        Optional<User> optionalUser = userService.findByUserId("user5");

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Feed saveFeed = Feed.builder()
                    .user(user)
                    .feedContent(feedSaveInputDto.getFeedContent())
                    .category(feedSaveInputDto.getCategory())
                    .inPublic(feedSaveInputDto.getIsPublic())
                    .build();

            log.info(saveFeed.toString());


            Feed savedFeed = feedService.save(saveFeed);

            FeedSaveOutput resultFeed = FeedSaveOutput.builder()
                    .userId(user.getUserId())
                    .feedNum(savedFeed.getFeedNum())
                    .feedContent(savedFeed.getFeedContent())
                    .feedDate(savedFeed.getFeedDate())
                    .feedReadCount(savedFeed.getFeedReadCount())
                    .category(savedFeed.getCategory())
                    .inPublic(savedFeed.getInPublic())
                    .build();

            log.info(resultFeed.toString());

            return ResponseEntity.status(HttpStatus.CREATED).body(resultFeed);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
