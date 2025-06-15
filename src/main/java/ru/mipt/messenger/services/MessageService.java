package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mipt.messenger.exceptions.NotEnoughAuthorityException;
import ru.mipt.messenger.models.ChatMember;
import ru.mipt.messenger.models.Content;
import ru.mipt.messenger.models.Message;
import ru.mipt.messenger.repositories.MessageRepository;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatMemberService chatMemberService;
    private final ContentService contentService;

    public Message sendMessage(Integer senderId, Integer chatId, String text) {
        Collection<ChatMember> memberships = chatMemberService.getMyMemberships(senderId);

        if (memberships.stream().noneMatch(o -> o.getChatId().equals(chatId))) {
            throw new NotEnoughAuthorityException("User " + senderId + " is not in chat " + chatId);
        }

        Content content = contentService.create(text);
        Message message = new Message(senderId, chatId, content.getContentId());
        return messageRepository.save(message);
    }

    public List<Message> getAllInChat(Integer chatId) {
        return messageRepository.findMessageByChatId(chatId);
    }
}
