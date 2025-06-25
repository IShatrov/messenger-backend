package ru.mipt.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import ru.mipt.messenger.models.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Integer> {
    Settings findByUserId(Integer userId);
}