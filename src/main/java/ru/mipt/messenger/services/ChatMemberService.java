package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mipt.messenger.models.ChatMember;
import ru.mipt.messenger.repositories.ChatMemberRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;

    public void createChatMember(ChatMember chatMember) {
        chatMemberRepository.save(chatMember);
    }

    public List<ChatMember> getMyMemberships(Integer userId) {
        return chatMemberRepository.findChatMembersByUserId(userId);
    }

    public List<ChatMember> getAll() {
        return chatMemberRepository.findAll();
    }
}
