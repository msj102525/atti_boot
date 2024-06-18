package org.ict.atti_boot.reply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.reply.model.entity.Reply;
import org.ict.atti_boot.reply.model.input.ReplySaveInputDto;
import org.ict.atti_boot.reply.service.ReplyService;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Reply reply = Reply.builder()
                    .user(user)
                    .feedNum(replySaveInputDto.getFeedNum())
                    .replyContent(replySaveInputDto.getReplyContent())
                    .build();

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

}
