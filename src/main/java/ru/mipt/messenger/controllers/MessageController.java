package ru.mipt.messenger.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.mipt.messenger.models.Content;
import ru.mipt.messenger.models.Message;
import ru.mipt.messenger.models.SecureUser;
import ru.mipt.messenger.services.MessageService;
import ru.mipt.messenger.dto.MessageWithTextDto;
import ru.mipt.messenger.repositories.MessageWithTextRepository;
import ru.mipt.messenger.services.ContentService;

import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageWithTextRepository messageWithTextRepository;
    private final ContentService contentService;
    private final WebSocketController webSocketController;

    @PostMapping("${message_base_url}")
    @Transactional
    public MessageWithTextDto sendMessage(@AuthenticationPrincipal SecureUser secureUser,
                            @RequestParam Integer chatId,
                            @RequestBody Content content) {
        var saved = messageService.sendMessage(secureUser.getUser().getUserId(), chatId, content.getText());
        var text = contentService.getById(saved.getContentId()).getText();
        var dto = new MessageWithTextDto(
            saved.getMessageId(),
            saved.getSenderId(),
            saved.getChatId(),
            text,
            saved.getSendDttm(),
            saved.isRead(),
            saved.getReplyToMessageId(),
            saved.getUpdatedDttm()
        );
        // Отправляем сообщение по WebSocket
        webSocketController.sendChatMessage(chatId, dto);
        return dto;
    }

    @GetMapping("${message_base_url}")
    public List<Message> getAllInChat(@RequestParam Integer chatId) {
        return messageService.getAllInChat(chatId);
    }

    @GetMapping("${message_base_url}/with-text/last")
    public List<MessageWithTextDto> getLastNMessagesWithText(@RequestParam Integer chatId, @RequestParam int n) {
        return messageWithTextRepository.findLastNMessagesWithText(chatId, PageRequest.of(0, n));
    }

    @GetMapping("${message_base_url}/with-text/before")
    public List<MessageWithTextDto> getNMessagesBeforeMessageWithText(@RequestParam Integer chatId, @RequestParam Integer messageId, @RequestParam int n) {
        return messageWithTextRepository.findNMessagesBeforeMessageWithText(chatId, messageId, PageRequest.of(0, n));
    }
}
