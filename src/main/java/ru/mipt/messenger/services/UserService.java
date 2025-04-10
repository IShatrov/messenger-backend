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

    /**
     * Creates a new user in the database. Please make sure that request body matches the following criteria:
     * 1) userId is null. This is required to correctly generate userId in the database.
     * It is not required to explicitly assign userId to null in the JSON since it defaults to null.
     * 2) nickname, firstname, phone, email and password are not null, not blank, have valid length and match patterns
     * if required.
     * 3) nickname, phone and email are unique in the database.
     * 4) If registrationDttm is not null, it matches pattern "yyyy-MM-dd'T'HH:mm:ss.SSSZ". RegistrationDttm
     * defaults to current UTC time even if it is not null in the request.
     * @see <a href="https://docs.spring.io/spring-framework/docs/3.2.4.RELEASE_to_4.0.0.M3/Spring%20Framework%203.2.4.RELEASE/org/springframework/format/annotation/DateTimeFormat.ISO.html">Spring docs</a>
     * 5) dateOfBirth is not null and matches pattern "yyyy-MM-dd".
     * 6) If secondName is not null, it has valid length.
     * 7) If status is not null, it is either "Banned" or "Active". If status is null, it defaults to "Active".
     * 8) If role is not null, it is either "Admin" or "User". If role is null, it defaults to "User".
     * 9) If profilePictureLink is not null, it has valid length.
     * @param user User to create
     * @throws DataIntegrityViolationException if userId is not null or other database error occurs.
     * @throws HttpMessageNotReadableException if JSON is invalid.
     */
    public void createUser(User user) throws DataIntegrityViolationException, HttpMessageNotReadableException {
        if (user.getUserId() != null) {
            throw new DataIntegrityViolationException("UserID must be null for new database entries");
        }

        userRepository.save(user);
    }

    /**
     * Reads user by userId.
     * @param id userId to read.
     * @return if such userId is present in the database, returns the user. If there is no user with such userId,
     * returns null.
     */
    public User readUserById(Integer id) {
        var result = userRepository.findById(id);

        return result.orElse(null);
    }

    public User readUserByNickname(String nickname) {
        var result = userRepository.findUserByNickname(nickname);

        return result.orElse(null);
    }

    /**
     * Reads users by firstname.
     * @param firstname firstname to read.
     * @return list of users whose firstname equals required one. If there are no such users, returns an empty list.
     */
    public List<User> readUsersByFirstname(String firstname) {
        return userRepository.findUsersByFirstname(firstname);
    }

    /**
     * Get all users from db
     * @return list of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Updates an existing user. Please note that you have to send the entire JSON in request body, even if you want
     * to update a single field. The JSON must follow the same criteria as for create() method except userId must
     * be of the user you want to update.
     * @param updateData user to update.
     * @throws ResourceNotFoundException if user does not exist
     */
    public void updateUser(User updateData) throws ResourceNotFoundException {
        if (!userRepository.existsById(updateData.getUserId())) {
            throw new ResourceNotFoundException("User with userId " + updateData.getUserId() + " does not exist");
        }

        userRepository.save(updateData);
    }

    /**
     * Deletes an existing user.
     * @param id user to delete.
     * @throws ResourceNotFoundException if user with id does not exist.
     */
    public void deleteUser(Integer id) throws ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with userId " + id + " does not exist");
        }

        userRepository.deleteById(id);
    }
}
