package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mipt.messenger.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findUsersByFirstname(String firstname);
}
