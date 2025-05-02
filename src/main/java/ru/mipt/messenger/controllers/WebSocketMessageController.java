package ru.mipt.messenger.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.mipt.messenger.models.Message;
import ru.mipt.messenger.models.WebSocketMessage;
import ru.mipt.messenger.services.MessageService;

@Controller
@AllArgsConstructor
public class WebSocketMessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    // actually maps to /ws/ws-chat since WebSocketConfig adds a prefix
    @MessageMapping("/ws-chat")
    @Transactional
    public void sendViaWebSocket(@Payload WebSocketMessage webSocketMessage) {
        Message message = messageService.sendMessage(webSocketMessage.getSenderId(),
                                                     webSocketMessage.getChatId(),
                                                     webSocketMessage.getText());

        // @SendTo didn`t work for dynamic URL
        String dest = "/topic/messages/" + webSocketMessage.getChatId();
        messagingTemplate.convertAndSend(dest, message);
    }
}
