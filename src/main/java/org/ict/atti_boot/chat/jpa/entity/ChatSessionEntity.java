package org.ict.atti_boot.chat.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.chat.model.dto.ChatSessionDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat_session")
public class ChatSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    private String senderId;
    private String receiverId;
    private int limitTime;


    @Column(name = "start_time", updatable = false)
    private LocalDateTime startTime;

    @PrePersist
    protected void onCreate() {
        this.startTime = LocalDateTime.now();
    }

    public ChatSessionDto toDto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ChatSessionDto.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .limitTime(limitTime)
                .startTime(startTime.format(formatter))
                .build();
    }


}
