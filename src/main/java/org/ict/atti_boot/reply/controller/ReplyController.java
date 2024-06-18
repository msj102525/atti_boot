package org.ict.atti_boot.reply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.reply.model.entity.Reply;
import org.ict.atti_boot.reply.model.input.ReplySaveInputDto;
import org.ict.atti_boot.reply.service.ReplyService;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reply")
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Reply> insertReply(@RequestBody ReplySaveInputDto replySaveInputDto) {
        log.info(replySaveInputDto.toString());

        Optional<User> optionalUser = userService.findByUserId("user02");

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Reply reply;

            if (replySaveInputDto.getReplyNum() == 0) {
                reply = Reply.builder()
                        .user(user)
                        .feedNum(replySaveInputDto.getFeedNum())
                        .replyContent(replySaveInputDto.getReplyContent())
                        .replySeq(1)
                        .replyLev(1)
                        .replyReplyRef(0)
                        .build();
            } else {
                reply = Reply.builder()
                        .user(user)
                        .feedNum(replySaveInputDto.getFeedNum())
                        .replyContent(replySaveInputDto.getReplyContent())
                        .replySeq(replySaveInputDto.getReplySeq() + 1)
                        .replyLev(2)
                        .replyReplyRef(replySaveInputDto.getReplyNum())
                        .build();
            }

            Reply resultReply = replyService.insertReply(reply);

            if (resultReply != null) {
                return ResponseEntity.ok(resultReply);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("")
    public ResponseEntity<List<Reply>> selectReplyByFeedNum(
            @RequestParam("feed") int feedNum,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        log.info("feedNum: " + feedNum);

        Pageable pageable = PageRequest.of(page, size);

        Page<Reply> replies = replyService.selectReplyByFeedNum(pageable, feedNum);

        List<Reply> repliesList = replies.getContent();

        return ResponseEntity.ok(repliesList);
    }


}
