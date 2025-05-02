package ru.mipt.messenger.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class WebSocketMessage {
    @Getter
    @Setter
    private Integer chatId;

    @Getter
    @Setter
    private Integer senderId;

    @Getter
    @Setter
    private String text;
}
