package org.ict.atti_boot.reply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.reply.model.entity.Reply;
import org.ict.atti_boot.reply.model.input.ReplySaveInputDto;
import org.ict.atti_boot.reply.model.output.ReplyListOutput;
import org.ict.atti_boot.reply.service.ReplyService;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<ReplyListOutput>> selectReplyByFeedNum(
            @RequestParam("feed") int feedNum,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1000") int size) {

        log.info("feedNum: " + feedNum);

        Pageable pageable = PageRequest.of(page, size);

        Page<Reply> replies = replyService.selectReplyByFeedNum(pageable, feedNum);

        List<ReplyListOutput> replyListOutputList = new ArrayList<>();

        replies.getContent().forEach(reply -> {
            if (reply.getReplyReplyRef() == 0) {
                List<ReplyListOutput> childReplies = new ArrayList<>();
                List<Reply> childReplyEntities = replyService.findAllByReplyReplyRef(reply.getReplyNum());
                childReplyEntities.forEach(childReply -> {
                    ReplyListOutput childReplyOutput = ReplyListOutput.builder()
                            .feedNum(childReply.getFeedNum())
                            .replyNum(childReply.getReplyNum())
                            .replyReplyRef(childReply.getReplyReplyRef())
                            .replyLev(childReply.getReplyLev())
                            .replySeq(childReply.getReplySeq())
                            .replyDate(childReply.getReplyDate())
                            .replyContent(childReply.getReplyContent())
                            .replyWriter(childReply.getUser().getUserId())
                            .replyUserType(childReply.getUser().getUserType())
                            .replyWriterProfileUrl(childReply.getUser().getProfileUrl())
                            .replyContent(childReply.getReplyContent())
                            .childReply(null) // 하위 댓글은 1단계만
                            .build();
                    childReplies.add(childReplyOutput);
                });

                ReplyListOutput replyListOutput = ReplyListOutput.builder()
                        .feedNum(reply.getFeedNum())
                        .replyNum(reply.getReplyNum())
                        .replyReplyRef(reply.getReplyReplyRef())
                        .replyLev(reply.getReplyLev())
                        .replySeq(reply.getReplySeq())
                        .replyDate(reply.getReplyDate())
                        .replyContent(reply.getReplyContent())
                        .replyWriter(reply.getUser().getUserId())
                        .replyUserType(reply.getUser().getUserType())
                        .replyWriterProfileUrl(reply.getUser().getProfileUrl())
                        .replyContent(reply.getReplyContent())
                        .childReply(childReplies)
                        .build();

                replyListOutputList.add(replyListOutput);
            }
        });

        return ResponseEntity.ok(replyListOutputList);
    }

}
