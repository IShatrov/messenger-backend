package ru.mipt.messenger.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mipt.messenger.exceptions.ResourceNotFoundException;
import ru.mipt.messenger.models.Contact;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.repositories.ContactRepository;
import ru.mipt.messenger.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public List<Contact> getAllContactsByOwnerId(Integer ownerId) {
        return contactRepository.findAllByOwnerId(ownerId);
    }

    public void addContact(String nickname, User owner) throws ResourceNotFoundException {
        User findingUser = userRepository.findUserByNickname(nickname).orElse(null);
        if (findingUser == null) {
            throw new ResourceNotFoundException("User with nickname " + nickname + " does not exist");
        }
        contactRepository.save(new Contact(owner.getUserId(), findingUser.getUserId()));
        contactRepository.save(new Contact(findingUser.getUserId(), owner.getUserId()));
    }
}
