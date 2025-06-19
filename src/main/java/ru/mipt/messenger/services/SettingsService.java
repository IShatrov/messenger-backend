package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import ru.mipt.messenger.dto.SettingsDto;
import ru.mipt.messenger.repositories.SettingsRepository;
import ru.mipt.messenger.models.Settings;

@Service
@AllArgsConstructor
public class SettingsService {
    private final SettingsRepository settingsRepository;

    public Settings getSettings(Integer userId) {
        return settingsRepository.findByUserId(userId);
    }

    public void updateTheme(Integer userId, boolean darkTheme) {
        Settings settings = settingsRepository.findByUserId(userId);
        
        settings.setDarkTheme(darkTheme);
        settingsRepository.save(settings);
    }

    private Settings createDefaultSettings(Integer userId) {
        Settings settings = new Settings();
        settings.setUserId(userId);
        return settingsRepository.save(settings);
    }
}
