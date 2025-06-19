package ru.mipt.messenger.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import ru.mipt.messenger.services.SettingsService;
import ru.mipt.messenger.controllers.SettingsController;
import ru.mipt.messenger.models.Settings;
import ru.mipt.messenger.dto.ThemeUpdateRequest;
import ru.mipt.messenger.models.SecureUser;

@RestController
@AllArgsConstructor
public class SettingsController {
    private final SettingsService settingsService;

    @GetMapping("${settings_base_url}/get")
    public Settings getSettings(@AuthenticationPrincipal SecureUser secureUser) {
        return settingsService.getSettings(secureUser.getUser().getUserId());
    }

    @PutMapping("${settings_base_url}/theme")
    public void updateTheme(@AuthenticationPrincipal SecureUser secureUser, @RequestBody Boolean darkTheme) {
        settingsService.updateTheme(secureUser.getUser().getUserId(), darkTheme);
    }
}