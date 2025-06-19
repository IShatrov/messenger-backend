package ru.mipt.messenger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import ru.mipt.messenger.services.SettingsService;
import ru.mipt.messenger.controllers.SettingsController;
import ru.mipt.messenger.dto.SettingsDto;
import ru.mipt.messenger.dto.ThemeUpdateRequest;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("${settings_base_url}")
    public List<SettingsDto> getSettings(Authentication authentication) {
        Integer userId = Integer.parseInt(authentication.getName());
        return List.of(settingsService.getSettings(userId));
    }

    @PutMapping("${settings_base_url}/theme")
    public List<SettingsDto> updateTheme(@RequestBody ThemeUpdateRequest request, Authentication authentication) {
        Integer userId = Integer.parseInt(authentication.getName());
        return  List.of(settingsService.getSettings(userId));
    }
}