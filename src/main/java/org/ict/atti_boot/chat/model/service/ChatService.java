package org.ict.atti_boot.chat.model.service;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.chat.jpa.entity.ChatMessageEntity;
import org.ict.atti_boot.chat.jpa.entity.ChatSessionEntity;
import org.ict.atti_boot.chat.jpa.repository.ChatMessageRepository;
import org.ict.atti_boot.chat.jpa.repository.ChatSessionRepository;
import org.ict.atti_boot.chat.model.dto.ChatMessageDto;
import org.ict.atti_boot.chat.model.dto.ChatSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatSessionDto createChatSession(ChatSessionDto chatSessionDto) {
        ChatSessionEntity chatSessionEntity = chatSessionDto.toEntity();
        chatSessionEntity = chatSessionRepository.save(chatSessionEntity);
        return chatSessionEntity.toDto();
    }

    // 일반 유저 값 찾기
     public ChatSessionDto getChatSessionByUserId(String userId) {
        ChatSessionEntity chatSessionEntity = chatSessionRepository.findTopBySenderIdOrderByStartTimeDesc(userId);
        if (chatSessionEntity != null) {
            return chatSessionEntity.toDto();
        } else {
            throw new RuntimeException("No chat session found for user: " + userId);
        }
    }

    // doctor 아이디로 값 찾기
    public ChatSessionDto getChatSessionByReceiverId(String receiverId) {
        ChatSessionEntity chatSessionEntity = chatSessionRepository.findTopByReceiverIdOrderByStartTimeDesc(receiverId);
        return chatSessionEntity != null ? chatSessionEntity.toDto() : null;
    }


    public ChatMessageDto saveChatMessage(ChatMessageDto chatMessageDto) {
        ChatSessionEntity chatSession = chatSessionRepository.findById(chatMessageDto.getChatId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid chatId"));
        ChatMessageEntity chatMessageEntity = chatMessageDto.toEntity(chatSession);
        chatMessageEntity = chatMessageRepository.save(chatMessageEntity);
        return chatMessageEntity.toDto();
    }

    public List<ChatMessageDto> getChatMessages(Long chatId) {
        List<ChatMessageEntity> messages = chatMessageRepository.findByChatSession_ChatId(chatId);
        return messages.stream()
                .map(ChatMessageEntity::toDto)
                .collect(Collectors.toList());
    }



    // 알람 테스트용
    public List<ChatSessionEntity> getRecentChatSessionsBySender(String senderId) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);
        return chatSessionRepository.findRecentSessionsBySenderId(senderId, startTime, endTime);
    }

    public List<ChatSessionEntity> getRecentChatSessionsByReceiver(String receiverId) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);
        return chatSessionRepository.findRecentSessionsByReceiverId(receiverId, startTime, endTime);
    }



    // 제한 시간 불러오기
    public ChatSessionDto getLimitTimeByChatId(String chatId){
        return chatSessionRepository.findLimitTimeByChatId(chatId).toDto();
    }

    public void endChatSession(Long chatId) {
        Optional<ChatSessionEntity> chatSessionOpt = chatSessionRepository.findById(chatId);
        if (chatSessionOpt.isPresent()) {
            ChatSessionEntity chatSession = chatSessionOpt.get();
            chatSession.setStatus(false);  // Assuming the status column is of type Boolean
            chatSessionRepository.save(chatSession);
        } else {
            throw new RuntimeException("Chat session not found");
        }
    }


}