package org.ict.atti_boot.chat.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.chat.jpa.entity.ChatMessageEntity;
import org.ict.atti_boot.chat.jpa.entity.ChatSessionEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDto {
    private Long messageId;
    private Long chatId;
    private String senderId;
    private String messageContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime timestamp;

    public ChatMessageEntity toEntity(ChatSessionEntity chatSession) {
        return ChatMessageEntity.builder()
                .messageId(messageId)
                .chatSession(chatSession)
                .senderId(senderId)
                .messageContent(messageContent)
                .timestamp(timestamp)
                .build();
    }
}
