package ru.mipt.messenger.dto;

import java.time.LocalDateTime;

public class MessageWithTextDto {
    public Integer messageId;
    public Integer senderId;
    public Integer chatId;
    public String text;
    public LocalDateTime sendDttm;
    public boolean isRead;
    public Integer replyToMessageId;
    public LocalDateTime updatedDttm;

    public MessageWithTextDto(Integer messageId, Integer senderId, Integer chatId, String text, LocalDateTime sendDttm, boolean isRead, Integer replyToMessageId, LocalDateTime updatedDttm) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.chatId = chatId;
        this.text = text;
        this.sendDttm = sendDttm;
        this.isRead = isRead;
        this.replyToMessageId = replyToMessageId;
        this.updatedDttm = updatedDttm;
    }
} 