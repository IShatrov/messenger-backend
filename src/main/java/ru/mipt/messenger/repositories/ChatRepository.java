package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import ru.mipt.messenger.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer>{
    List<Chat> findChatByName(String name);
}
