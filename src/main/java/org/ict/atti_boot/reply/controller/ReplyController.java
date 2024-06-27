package org.ict.atti_boot.reply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.reply.model.entity.Reply;
import org.ict.atti_boot.reply.model.input.ReplySaveInputDto;
import org.ict.atti_boot.reply.model.output.ReplyListOutput;
import org.ict.atti_boot.reply.service.ReplyService;
import org.ict.atti_boot.security.jwt.util.JWTUtil;
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
    private final JWTUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<Reply> insertReply(@RequestHeader("Authorization") String token, @RequestBody ReplySaveInputDto replySaveInputDto) {
        log.info(replySaveInputDto.toString());
        String userId = jwtUtil.getUserIdFromToken(token);

        Optional<User> optionalUser = userService.findByUserId(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = optionalUser.get();
        Reply reply = createReply(replySaveInputDto, user);

        Reply resultReply = replyService.insertReply(reply);

        if (resultReply != null) {
            return ResponseEntity.ok(resultReply);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
                List<Reply> childReplyEntities = replyService.findAllByReplyReplyRef(reply.getReplyNum());
                List<ReplyListOutput> childReplies = convertToReplyListOutputs(childReplyEntities);

                ReplyListOutput replyListOutput = convertToReplyListOutput(reply, childReplies);
                replyListOutputList.add(replyListOutput);
            }
        });

        return ResponseEntity.ok(replyListOutputList);
    }

    private Reply createReply(ReplySaveInputDto replySaveInputDto, User user) {
        return Reply.builder()
                .user(user)
                .feedNum(replySaveInputDto.getFeedNum())
                .replyContent(replySaveInputDto.getReplyContent())
                .replySeq(replySaveInputDto.getReplyNum() == 0 ? 1 : replySaveInputDto.getReplySeq() + 1)
                .replyLev(replySaveInputDto.getReplyNum() == 0 ? 1 : 2)
                .replyReplyRef(replySaveInputDto.getReplyNum())
                .build();
    }

    private List<ReplyListOutput> convertToReplyListOutputs(List<Reply> replies) {
        List<ReplyListOutput> replyListOutputs = new ArrayList<>();
        replies.forEach(reply -> {
            ReplyListOutput replyListOutput = convertToReplyListOutput(reply, null);
            replyListOutputs.add(replyListOutput);
        });
        return replyListOutputs;
    }

    private ReplyListOutput convertToReplyListOutput(Reply reply, List<ReplyListOutput> childReplies) {
        return ReplyListOutput.builder()
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
                .childReply(childReplies)
                .build();
    }
}
