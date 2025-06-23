package ru.mipt.messenger.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.mipt.messenger.exceptions.ResourceNotFoundException;
import ru.mipt.messenger.models.Chat;
import ru.mipt.messenger.models.ChatMember;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.repositories.ChatRepository;
import ru.mipt.messenger.repositories.UserRepository;
import ru.mipt.messenger.controllers.ChatController.PrivateChatResponse;

@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMemberService chatMemberService;
    private final UserRepository userRepository;

    public Chat readChatById(Integer id) {
        var result = chatRepository.findById(id);
        return result.orElse(null);
    }

    public List<Chat> readChatsByName(String name) {
        return chatRepository.findChatByName(name);
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    /*  Нужно ли обновлять чаты
    public void updateChat(Chat updateData) throws ResourceNotFoundException {
        if (!chatRepository.existsById(updateData.getChatId())) {
            throw new ResourceNotFoundException("Chat with chatId " + updateData.getChatId() + " does not exist");
        }

        chatRepository.save(updateData);
    }
    */

    @Transactional
    public void createChat(Chat chat, Integer creatorId)
            throws DataIntegrityViolationException, HttpMessageNotReadableException {
        if (chat.getChatId() != null) {
            throw new DataIntegrityViolationException("ChatID must be null for new database entries");
        }

        Chat createdChat = chatRepository.save(chat);
        chatMemberService.createChatMember(new ChatMember(creatorId, createdChat.getChatId()));
    }

    @Transactional
    public void createPrivateChat(Integer creatorId, Integer companionId) {
        Chat chat = new Chat();
        chat.setType(ru.mipt.messenger.types.chattypes.Type.Private);
        chat.setName("");
        chat.setPictureLink("");
        chat.setDescription("");
        Chat createdChat = chatRepository.save(chat);
        chatMemberService.createChatMember(new ChatMember(creatorId, createdChat.getChatId()));
        chatMemberService.createChatMember(new ChatMember(companionId, createdChat.getChatId()));
    }

    @Transactional
    public PrivateChatResponse createPrivateChatWithResponse(Integer creatorId, Integer companionId) {
        Chat chat = new Chat();
        chat.setType(ru.mipt.messenger.types.chattypes.Type.Private);
        chat.setName("");
        chat.setPictureLink("");
        chat.setDescription("");
        Chat createdChat = chatRepository.save(chat);
        chatMemberService.createChatMember(new ChatMember(creatorId, createdChat.getChatId()));
        chatMemberService.createChatMember(new ChatMember(companionId, createdChat.getChatId()));
        User creator = userRepository.findById(creatorId).orElse(null);
        User companion = userRepository.findById(companionId).orElse(null);
        return new PrivateChatResponse(
            createdChat.getChatId(),
            createdChat.getChatType().toString(),
            creator,
            companion,
            createdChat.getCreatedDttm().toString()
        );
    }

    public void deleteChat(Integer id) throws ResourceNotFoundException {
        if (!chatRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chat with chatId " + id + " does not exist");
        }
        chatRepository.deleteById(id);
    }
}
