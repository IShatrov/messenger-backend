package ru.mipt.messenger.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.messenger.models.Message;
import ru.mipt.messenger.models.SecureUser;
import ru.mipt.messenger.services.MessageService;

import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("${message_base_url}")
    @Transactional
    public void sendMessage(@AuthenticationPrincipal SecureUser secureUser,
                            @RequestParam Integer chatId,
                            @RequestParam String text) {
        messageService.sendMessage(secureUser.getUser().getUserId(), chatId, text);
    }

    @GetMapping("${message_base_url}")
    public List<Message> getAllInChat(@RequestParam Integer chatId) {
        return messageService.getAllInChat(chatId);
    }
}
