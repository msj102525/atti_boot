package org.ict.atti_boot.reply.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.reply.model.entity.Reply;
import org.ict.atti_boot.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public Reply insertReply(Reply reply) {
        return replyRepository.save(reply);
    }

}
