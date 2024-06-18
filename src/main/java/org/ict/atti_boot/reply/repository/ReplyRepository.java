package org.ict.atti_boot.reply.repository;

import org.ict.atti_boot.reply.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
}
