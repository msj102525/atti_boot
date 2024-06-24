package org.ict.atti_boot.chat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.chat.jpa.entity.ChatSessionEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatSessionDto {
    private Long chatId;
    private String senderId;
    private String receiverId;
    private String startTime;
    private int limitTime;
    private boolean status;


    public ChatSessionEntity toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedStartTime = LocalDateTime.parse(startTime, formatter);
        return ChatSessionEntity.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .limitTime(limitTime)
                .status(status)
                .startTime(parsedStartTime)
                .build();
    }



}
