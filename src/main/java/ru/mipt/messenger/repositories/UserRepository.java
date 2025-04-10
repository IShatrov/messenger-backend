package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import ru.mipt.messenger.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUsersByFirstname(String firstname);

    Optional<User> findUserByNickname(String nickname);
}
