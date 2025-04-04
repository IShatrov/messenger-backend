package ru.mipt.messenger.repositories;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.types.usertypes.Role;
import ru.mipt.messenger.types.usertypes.Status;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@AllArgsConstructor
class UserRepositoryTest {

//    private final UserRepository userRepository;

    @Test
    public void testUserRepository() {
//        userRepository.save(new User("pelmeshka", "Kirill", "Pavlovskiy", LocalDateTime.now(), null, Status.Active, Role.User, null, true, null, null, null, "123"));
//        assertEquals("pelmeshka", userRepository.findUsersByFirstname("Kirill").getFirst().getNickname());
    }
}