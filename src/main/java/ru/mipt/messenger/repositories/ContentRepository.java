package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mipt.messenger.models.Content;

public interface ContentRepository extends JpaRepository<Content, Integer> {
}
