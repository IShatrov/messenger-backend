package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mipt.messenger.models.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findAllByOwnerId(Integer id);
}
