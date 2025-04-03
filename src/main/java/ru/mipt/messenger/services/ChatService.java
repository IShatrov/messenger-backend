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

    /**
     * Creates a new chat in the database. Please make sure that request body matches the following criteria:
     * 1) ChatId is null. This is required to correctly generate chatId in the database.
     * It is not required to explicitly assign chatId to null in the JSON since it defaults to null.
     * 2) Chat name is not null, not blank, have valid length 
     * 3) If creationDttm is not null, it matches pattern "yyyy-MM-dd'T'HH:mm:ss.SSSZ". creationDttm 
     * defaults to current UTC time even if it is not null in the request.
     * @see <a href="https://docs.spring.io/spring-framework/docs/3.2.4.RELEASE_to_4.0.0.M3/Spring%20Framework%203.2.4.RELEASE/org/springframework/format/annotation/DateTimeFormat.ISO.html">Spring docs</a>
     * 4) Type cant be nul
     * @param chat Chat to create
     * @throws DataIntegrityViolationException if chatId is not null or other database error occurs.
     * @throws MethodArgumentNotValidException if any field of chat is invalid.
     * @throws HttpMessageNotReadableException if JSON is invalid.
     */
    public void createChat(Chat chat) throws DataIntegrityViolationException, HttpMessageNotReadableException {
        if (chat.getChatId() != null) {
            throw new DataIntegrityViolationException("ChatID must be null for new database entries");
        }

        chatRepository.save(chat);
    }

    /**
     * Reads chat by chatId.
     * @param id chatId to read.
     * @return if such chatId is present in the database, returns the chat. If there is no chat with such chatId,
     * returns null.
     */
    public Chat readChatById(Integer id) {
        var result = chatRepository.findById(id);

        return result.orElse(null);
    }

    /**
     * Reads chats by firstname.
     * @param firstname firstname to read.
     * @return list of chats whose firstname equals required one. If there are no such chats, returns an empty list.
     */
    public List<Chat> readChatsByName(String firstname) {
        return chatRepository.findChatsByFirstname(firstname);
    }

    /**
     * Get all chats from db
     * @return list of chats
     */
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

    /**
     * Deletes an existing chat.
     * @param id chat to delete.
     * @throws ResourceNotFoundException if chat with id does not exist.
     */
    public void deleteChat(Integer id) throws ResourceNotFoundException {
        if (!chatRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chat with chatId " + id + " does not exist");
        }

        chatRepository.deleteById(id);
    }
}
