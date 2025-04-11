package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.mipt.messenger.exceptions.ResourceNotFoundException;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User readUserById(Integer id) {
        var result = userRepository.findById(id);
        return result.orElse(null);
    }

    public User readUserByNickname(String nickname) {
        var result = userRepository.findUserByNickname(nickname);
        return result.orElse(null);
    }

    public List<User> readUsersByFirstname(String firstname) {
        return userRepository.findUsersByFirstname(firstname);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) throws DataIntegrityViolationException, HttpMessageNotReadableException {
        if (user.getUserId() != null) {
            throw new DataIntegrityViolationException("UserID must be null for new database entries");
        }

        userRepository.save(user);
    }

    public void updateUser(User updateData) throws ResourceNotFoundException {
        if (!userRepository.existsById(updateData.getUserId())) {
            throw new ResourceNotFoundException("User with userId " + updateData.getUserId() + " does not exist");
        }
        userRepository.save(updateData);
    }

    public void deleteUser(Integer id) throws ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with userId " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }
}
