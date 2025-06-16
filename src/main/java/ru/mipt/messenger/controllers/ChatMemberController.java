package ru.mipt.messenger.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.messenger.models.ChatMember;
import ru.mipt.messenger.models.SecureUser;
import ru.mipt.messenger.services.ChatMemberService;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatMemberController {
    private final ChatMemberService chatMemberService;

    @PutMapping("${chat_member_base_url}")
    public void add(@RequestParam Integer userIdToAdd, @RequestParam Integer chatIdToAdd) {
        chatMemberService.createChatMember(new ChatMember(userIdToAdd, chatIdToAdd));
    }

    @GetMapping("${chat_member_base_url}/my")
    public List<ChatMember> getMyMemberships(@AuthenticationPrincipal SecureUser secureUser) {
        return chatMemberService.getMyMemberships(secureUser.getUser().getUserId());
    }

    @GetMapping("${chat_member_base_url}/all")
    public List<ChatMember> getAll() {
        return chatMemberService.getAll();
    }
}
