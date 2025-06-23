package ru.mipt.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Отправка нового сообщения в чат
    public void sendChatMessage(Integer chatId, Object message) {
        messagingTemplate.convertAndSend("/topic/chat/" + chatId + "/messages", message);
    }

    // Уведомление о создании нового чата для пользователя
    public void sendNewChatEvent(Integer userId, Object chatInfo) {
        messagingTemplate.convertAndSend("/topic/user/" + userId + "/new-chat", chatInfo);
    }

    // Заглушка: уведомление о добавлении в контакты
    public void sendAddedToContactsEvent(Integer userId, String contactInfo) {
        messagingTemplate.convertAndSend("/topic/user/" + userId + "/added-to-contacts", contactInfo);
    }
} 