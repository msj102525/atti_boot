package org.ict.atti_boot.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.chat.jpa.entity.ChatSessionEntity;
import org.ict.atti_boot.chat.model.dto.ChatMessageDto;
import org.ict.atti_boot.chat.model.dto.ChatSessionDto;
import org.ict.atti_boot.chat.model.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // 결제 시 세션에 저장
    @PostMapping("/session")
    public ResponseEntity<ChatSessionDto> createChatSession(@RequestBody ChatSessionDto chatSessionDto) {
        log.info(chatSessionDto + "consul");
        ChatSessionDto createdSession = chatService.createChatSession(chatSessionDto);
        return ResponseEntity.ok(createdSession);
    }

    // user 타입별로 세션 정보 불러오기
    @GetMapping("/session/{userId}")
    public ResponseEntity<ChatSessionDto> getChatSessionByUserId(
            @PathVariable String userId, @RequestParam String type) {
        ChatSessionDto chatSession;
        if ("sender".equals(type)) {
            chatSession = chatService.getChatSessionByUserId(userId);
        } else if ("receiver".equals(type)) {
            chatSession = chatService.getChatSessionByReceiverId(userId);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(chatSession);
    }


    // 메시지 저장
    @PostMapping("/message")
    public ResponseEntity<ChatMessageDto> saveMessage(@RequestBody ChatMessageDto chatMessageDto) {
        log.info(chatMessageDto + "ㅂㅈㄷㅂㅈㄷ");
        ChatMessageDto savedMessage = chatService.saveChatMessage(chatMessageDto);
        return ResponseEntity.ok(savedMessage);
    }

    // 메시지 불러오기;
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessageDto>> getMessages(
            @RequestParam String senderId,
            @RequestParam Long chatId
    ) {


        List<ChatMessageDto> messages = chatService.getChatMessages(chatId);

        return ResponseEntity.ok(messages);
    }

    // 알람 테스트용
    @GetMapping("/session/alarm/{userId}")
    public ResponseEntity<List<ChatSessionEntity>> getChatSessionByUserIdAlarm(
            @PathVariable String userId, @RequestParam String type) {

        log.info(userId + "con123");
        log.info(type + "con123");

        List<ChatSessionEntity> chatSessions;
        if ("sender".equals(type)) {
            chatSessions = chatService.getRecentChatSessionsBySender(userId);
        } else if ("receiver".equals(type)) {
            // Implement receiver logic if needed
            chatSessions = chatService.getRecentChatSessionsByReceiver(userId); // Placeholder
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(chatSessions);
    }


    // 채팅방 시간 제한 불러오기
    @GetMapping("/time/{chatId}")
    public ResponseEntity<ChatSessionDto> getLimitTimeByChatId(
            @PathVariable String chatId) {

        ChatSessionDto chatSession = chatService.getLimitTimeByChatId(chatId);
        return ResponseEntity.ok(chatSession);
    }



}