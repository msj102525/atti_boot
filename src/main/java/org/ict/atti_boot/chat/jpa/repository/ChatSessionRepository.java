package org.ict.atti_boot.chat.jpa.repository;


import org.ict.atti_boot.chat.jpa.entity.ChatSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSessionEntity, Long> {

    ChatSessionEntity findTopBySenderIdOrderByStartTimeDesc(String senderId);

    ChatSessionEntity findTopByReceiverIdOrderByStartTimeDesc(String receiverId);

    // 알람 테스트용
    @Query("SELECT c FROM ChatSessionEntity c WHERE c.senderId = :senderId AND c.startTime BETWEEN :startTime AND :endTime ORDER BY c.startTime DESC")
    List<ChatSessionEntity> findRecentSessionsBySenderId(
        @Param("senderId") String senderId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT c FROM ChatSessionEntity c WHERE c.receiverId = :receiverId AND c.startTime BETWEEN :startTime AND :endTime ORDER BY c.startTime DESC")
    List<ChatSessionEntity> findRecentSessionsByReceiverId(
        @Param("receiverId") String receiverId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT cs FROM ChatSessionEntity cs WHERE cs.chatId = :chatId")
    ChatSessionEntity findLimitTimeByChatId(@Param("chatId") String chatId);


}
