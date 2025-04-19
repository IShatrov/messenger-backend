package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mipt.messenger.models.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findMessageByChatId(Integer chatId);
}
