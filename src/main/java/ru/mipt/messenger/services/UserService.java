package ru.mipt.messenger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User readUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    public List<User> readUsersByNickname(String nickname) {
        return userRepository.findUsersByNickname(nickname);
    }

    public void updateUser(Integer id, User newUser) {
        userRepository.save(newUser);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
