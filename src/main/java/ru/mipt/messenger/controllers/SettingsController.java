package ru.mipt.messenger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<SettingsDto> getSettings(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(settingsService.getSettings(userId));
    }

    @PutMapping("/theme")
    public ResponseEntity<Void> updateTheme(@RequestBody ThemeUpdateRequest request, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        settingsService.updateTheme(userId, request.darkTheme());
        return ResponseEntity.ok().build();
    }
}