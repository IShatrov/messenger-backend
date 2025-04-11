package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.mipt.messenger.exceptions.ResourceNotFoundException;
import ru.mipt.messenger.models.Chat;
import ru.mipt.messenger.repositories.ChatRepository;

@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

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

    public void createChat(Chat chat) throws DataIntegrityViolationException, HttpMessageNotReadableException {
        if (chat.getChatId() != null) {
            throw new DataIntegrityViolationException("ChatID must be null for new database entries");
        }
        chatRepository.save(chat);
    }

    public void deleteChat(Integer id) throws ResourceNotFoundException {
        if (!chatRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chat with chatId " + id + " does not exist");
        }
        chatRepository.deleteById(id);
    }
}
