package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mipt.messenger.models.ChatMember;
import ru.mipt.messenger.models.UserChatId;

import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember, UserChatId> {
    List<ChatMember> findChatMembersByUserId(Integer userId);
}
