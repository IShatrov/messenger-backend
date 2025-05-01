package ru.mipt.messenger.controllers;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.messenger.exceptions.ResourceNotFoundException;
import ru.mipt.messenger.models.Contact;
import ru.mipt.messenger.models.SecureUser;
import ru.mipt.messenger.services.ContactService;


import java.util.List;

@RestController
@RequestMapping("${contact_base_url}")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @Operation
    @GetMapping()
    public List<Contact> getAllContacts(@AuthenticationPrincipal SecureUser secureUser) {
        return contactService.getAllContactsByOwnerId(secureUser.getUser().getUserId());
    }

    @Operation
    @PostMapping()
    public void addContact(@AuthenticationPrincipal SecureUser secureUser, @RequestParam String nickname) throws ResourceNotFoundException {
        contactService.addContact(nickname, secureUser.getUser());
    }
}
