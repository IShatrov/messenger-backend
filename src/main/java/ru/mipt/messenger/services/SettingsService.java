package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ru.mipt.messenger.dto.SettingsDto;
import ru.mipt.messenger.repositories.SettingsRepository;
import ru.mipt.messenger.models.Settings;

@Service
@AllArgsConstructor
public class SettingsService {
    private final SettingsRepository settingsRepository;

    public SettingsDto getSettings(Integer userId) {
        Settings settings = settingsRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultSettings(userId));
        
        return new SettingsDto(
            settings.isDarkTheme(),
            settings.isShowDateOfBirth(),
            settings.isChatYourselfDefault(),
            settings.isContactAutoAccept()
        );
    }

    public void updateTheme(Integer userId, boolean darkTheme) {
        Settings settings = settingsRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultSettings(userId));
        
        settings.setDarkTheme(darkTheme);
        settingsRepository.save(settings);
    }

    private Settings createDefaultSettings(Integer userId) {
        Settings settings = new Settings();
        settings.setUserId(userId);
        return settingsRepository.save(settings);
    }
}
