package ru.mipt.messenger.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer messageId;

    @Getter
    private Integer senderId;

    @Getter
    private Integer chatId;

    @Getter
    private Integer contentId;

    @Getter
    private LocalDateTime sendDttm;

    @Getter
    private boolean isRead;

    @Getter
    private Integer replyToMessageId;

    @Getter
    private LocalDateTime updatedDttm;

    public Message() {
        this.sendDttm = LocalDateTime.now(ZoneOffset.UTC);
    }

    public Message(Integer senderId, Integer chatId, Integer contentId) {
        this.senderId = senderId;
        this.chatId = chatId;
        this.contentId = contentId;
        this.sendDttm = LocalDateTime.now(ZoneOffset.UTC);
    }
}
