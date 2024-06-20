package org.ict.atti_boot.chat.jpa.repository;


import org.ict.atti_boot.chat.jpa.entity.ChatSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSessionEntity, Long> {

    ChatSessionEntity findTopBySenderIdOrderByStartTimeDesc(String senderId);

    ChatSessionEntity findTopByReceiverIdOrderByStartTimeDesc(String receiverId);

}
