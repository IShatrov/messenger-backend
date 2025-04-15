package ru.mipt.messenger.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "chat_members")
@IdClass(UserChatId.class)
public class ChatMember {
    @Id
    @Getter
    private Integer userId;

    @Id
    @Getter
    private Integer chatId;

    @Getter
    private LocalDateTime joinDttm;

    @Getter
    private LocalDateTime leaveDttm;

    public ChatMember() {
        this.joinDttm = LocalDateTime.now(ZoneOffset.UTC);
    }

    public ChatMember(Integer userId, Integer chatId) {
        this.userId = userId;
        this.chatId = chatId;
        this.joinDttm = LocalDateTime.now(ZoneOffset.UTC);
    }
}
