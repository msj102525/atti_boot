package org.ict.atti_boot.chat.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.chat.model.dto.ChatMessageDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat_message")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatSessionEntity chatSession;

    @Column(name = "sender_id")
    private String senderId;

    @Column(name = "message_content", columnDefinition = "CLOB")
    private String messageContent;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public ChatMessageDto toDto() {
        return ChatMessageDto.builder()
                .messageId(messageId)
                .chatId(chatSession.getChatId())
                .senderId(senderId)
                .messageContent(messageContent)
                .timestamp(timestamp)
                .build();
    }
}
