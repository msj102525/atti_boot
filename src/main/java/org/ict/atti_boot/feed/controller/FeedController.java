package org.ict.atti_boot.feed.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.feed.model.entity.Feed;
import org.ict.atti_boot.feed.model.input.FeedUpdateInputDto;
import org.ict.atti_boot.feed.model.output.FeedListOutput;
import org.ict.atti_boot.reply.model.entity.Reply;
import org.ict.atti_boot.feed.model.input.FeedSaveInputDto;
import org.ict.atti_boot.feed.model.output.FeedSaveOutput;
import org.ict.atti_boot.feed.repository.FeedContentVo;
import org.ict.atti_boot.feed.service.FeedService;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public ResponseEntity<List<FeedListOutput>> selectAllFeeds(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "subCategory", required = false) String subCategory,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "searchData", required = false) String searchData,
            @RequestHeader(value = "Authorization", required = false) String token
    ) {

        String loginUserId = null;
        if (token != null) {
            loginUserId = jwtUtil.getUserIdFromToken(token);
        }

        log.info("selectAllFeeds called");
        log.info("Page:" + page);
        log.info("Size:" + size);
        log.info("Category:" + category);
        log.info("SubCategory:" + subCategory);
        log.info("SearchData:" + searchData);

        Sort sort = null;
        Page<Feed> feeds = null;
        Pageable pageable = null;

        if (searchData == null || searchData.isEmpty()) {
            if ("공감순".equals(subCategory)) {
                log.info("공감순 : " + subCategory);
                pageable = PageRequest.of(page, size);
                feeds = feedService.selectAllFeedsByLikeCount(pageable, category);
            } else if ("전문 답변".equals(subCategory)) {
                log.info("전문 답변 : " + subCategory);
                sort = Sort.by(Sort.Direction.DESC, "feedDate");
                pageable = PageRequest.of(page, size, sort);
                feeds = feedService.selectAllFeedsHasDocterReply(pageable, category);
            } else {
                log.info("최신순 : " + subCategory);
                sort = Sort.by(Sort.Direction.DESC, "feedDate");
                pageable = PageRequest.of(page, size, sort);
                feeds = feedService.selectAllFeeds(pageable, category);
            }
        } else {
            log.info("ELSE SearchData:" + searchData);
            sort = Sort.by(Sort.Direction.DESC, "feedDate");
            pageable = PageRequest.of(page, size, sort);
            feeds = feedService.selectAllFeedsBySearchData(pageable, category, searchData);
        }

        List<FeedListOutput> feedListOutputList = buildFeedListOutput(feeds, loginUserId);
        return ResponseEntity.ok().body(feedListOutputList);
    }

    @GetMapping("/{feedNum}")
    public ResponseEntity<FeedListOutput> selectFeedByFeedNum(
            @PathVariable(name = "feedNum") int feedNum,
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        String loginUserId = null;
        if (token != null) {
            loginUserId = jwtUtil.getUserIdFromToken(token);
        }

        Feed feed = feedService.selectFeedById(feedNum);
        if (feed != null) {
            FeedListOutput feedListOutput = buildSingleFeedOutput(feed, loginUserId);
            return ResponseEntity.ok().body(feedListOutput);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/similar/{feedNum}")
    public ResponseEntity<List<FeedListOutput>> selectSimilarFeedByFeedNum(
            @PathVariable int feedNum,
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        log.info("selectSimilarFeedByFeedNum called : {}" + feedNum + token);
        String loginUserId = null;
        if (token != null) {
            loginUserId = jwtUtil.getUserIdFromToken(token);
        }

        log.info("loginUserId:" + loginUserId);

        Feed searchFeed = feedService.selectFeedById(feedNum);
        if (searchFeed != null) {
            Sort sort = Sort.by(Sort.Direction.DESC, "feedDate");
            Pageable pageable = PageRequest.of(0, 20, sort);

            Page<Feed> feeds = feedService.selectAllFeeds(pageable, searchFeed.getCategory());
            List<FeedListOutput> feedListOutputList = buildFeedListOutput(feeds, loginUserId);

            FeedListOutput searchFeedOutput = buildSingleFeedOutput(searchFeed, loginUserId);
            feedListOutputList.add(0, searchFeedOutput);

            return ResponseEntity.ok().body(feedListOutputList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<FeedSaveOutput> insertFeed(@RequestHeader("Authorization") String token, @RequestBody FeedSaveInputDto feedSaveInputDto) {
        log.info(feedSaveInputDto.toString());
        log.info(token);

        String userId = jwtUtil.getUserIdFromToken(token);

        Optional<User> optionalUser = userService.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Feed saveFeed = Feed.builder()
                    .user(user)
                    .feedContent(feedSaveInputDto.getFeedContent())
                    .category(feedSaveInputDto.getCategory())
                    .inPublic(feedSaveInputDto.getInPublic())
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

    @PutMapping("")
    public ResponseEntity<Void> updateFeed(@RequestHeader("Authorization") String token, @RequestBody FeedUpdateInputDto feedUpdateInputDto
    ) {
        log.info(feedUpdateInputDto.toString());
        String userId = jwtUtil.getUserIdFromToken(token);
        log.info(userId);


        Optional<User> optionalUser = userService.findByUserId(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Feed selectFeedById = feedService.selectFeedById(feedUpdateInputDto.getFeedNum());

            if (selectFeedById != null) {
                selectFeedById.setFeedContent(feedUpdateInputDto.getFeedContent());
                selectFeedById.setCategory(feedUpdateInputDto.getCategory());
                selectFeedById.setInPublic(feedUpdateInputDto.getInPublic());
                selectFeedById.setFeedDate(LocalDateTime.now());
                selectFeedById.setUser(user);

                feedService.save(selectFeedById);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{feedNum}")
    public ResponseEntity<Void> deleteFeedByFeedNum(@PathVariable int feedNum) {
        log.info(String.valueOf(feedNum));
        feedService.deleteFeedById(feedNum);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private List<FeedListOutput> buildFeedListOutput(Page<Feed> feeds, String loginUserId) {
        List<FeedListOutput> feedListOutputList = new ArrayList<>();
        feeds.getContent().forEach(feed -> {
            feedListOutputList.add(buildSingleFeedOutput(feed, loginUserId));
        });
        return feedListOutputList;
    }

    private FeedListOutput buildSingleFeedOutput(Feed feed, String loginUserId) {
        List<Reply> replies = feed.getReplies();
        if (replies != null) {
            replies.sort(Comparator.comparing(Reply::getReplyDate).reversed());
        }

        User user = feed.getUser();
        String userId = (user != null) ? user.getUserId() : "Unknown";
        String profileUrl = (user != null) ? user.getProfileUrl() : "";

        FeedListOutput.FeedListOutputBuilder feedListOutputBuilder = FeedListOutput.builder()
                .feedWriterId(userId)
                .feedWriterProfileUrl(profileUrl)
                .category(feed.getCategory())
                .feedNum(feed.getFeedNum())
                .feedContent(feed.getFeedContent())
                .feedDate(feed.getFeedDate())
                .inPublic(feed.getInPublic())
                .replyCount((replies != null) ? replies.size() : 0)
                .likeCount((feed.getLikeHistories() != null) ? feed.getLikeHistories().size() : 0)
                .loginUserIsLiked(feed.getLikeHistories() != null && feed.getLikeHistories().stream()
                        .anyMatch(reply -> reply.getUserId().equals(loginUserId)))
                .dComentExist(replies != null && replies.stream()
                        .anyMatch(reply -> reply.getUser().getUserType() == 'D'));

        if (replies != null) {
            Reply doctorReply = replies.stream()
                    .filter(reply -> reply.getUser().getUserType() == 'D')
                    .findFirst()
                    .orElse(null);

            if (doctorReply != null) {
                User doctorUser = doctorReply.getUser();
                feedListOutputBuilder
                        .docterName(doctorUser != null ? doctorUser.getUserName() : "No doctor user found")
                        .docterImgUrl(doctorUser != null ? doctorUser.getProfileUrl() : "")
                        .docterComent(doctorReply.getReplyContent());
            } else {
                feedListOutputBuilder
                        .docterName("No doctor user found")
                        .docterImgUrl("")
                        .docterComent("No doctor user found");
            }
        } else {
            feedListOutputBuilder
                    .docterName("No doctor user found")
                    .docterImgUrl("")
                    .docterComent("No doctor user found");
        }

        return feedListOutputBuilder.build();
    }
}
