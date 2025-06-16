package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mipt.messenger.models.ChatMember;
import ru.mipt.messenger.models.Contact;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import ru.mipt.messenger.dto.UserDto;

@Service
@AllArgsConstructor
public class SessionInfoService {
    private final UserService userService;
    private final ChatMemberService chatMemberService;
    private final ContactService contactService;

    public SessionInfoResponse getSessionInfo(Integer userId) {
        SessionInfoResponse resp = new SessionInfoResponse();
        resp.currentUser = new UserDto(userService.readUserById(userId));
        resp.myChats = chatMemberService.getMyMemberships(userId);
        // Получаем chatId всех чатов пользователя
        Set<Integer> myChatIds = resp.myChats.stream()
            .map(ChatMember::getChatId)
            .collect(Collectors.toSet());
        // Оставляем только участников этих чатов
        resp.allChatMembers = chatMemberService.getAll().stream()
            .filter(cm -> myChatIds.contains(cm.getChatId()))
            .collect(Collectors.toList());
        resp.contacts = contactService.getAllContactsByOwnerId(userId);

        Set<Integer> companionIds = resp.allChatMembers.stream()
            .filter(cm -> resp.myChats.stream().anyMatch(my -> my.getChatId().equals(cm.getChatId())))
            .map(ChatMember::getUserId)
            .filter(id -> !id.equals(userId))
            .collect(Collectors.toSet());
        resp.companions = companionIds.stream()
            .map(userService::readUserById)
            .filter(u -> u != null)
            .map(UserDto::new)
            .collect(Collectors.toList());

        return resp;
    }

    public static class SessionInfoResponse {
        public UserDto currentUser;
        public List<ChatMember> myChats;
        public List<ChatMember> allChatMembers;
        public List<UserDto> companions;
        public List<Contact> contacts;
    }
} 