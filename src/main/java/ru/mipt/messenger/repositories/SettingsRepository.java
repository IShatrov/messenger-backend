package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import ru.mipt.messenger.models.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Optional<Settings> findByUserId(Long userId);
}