package ru.mipt.messenger.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserChatId {
    @Getter
    @Setter
    private Integer userId;

    @Getter
    @Setter
    private Integer chatId;
}
