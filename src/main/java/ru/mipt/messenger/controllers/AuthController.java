package ru.mipt.messenger.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.messenger.models.SecureUser;

@RestController
public class AuthController {

    @PostMapping("/login") // Изменено на POST
    public ResponseEntity<String> login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            SecureUser secureUser = (SecureUser) authentication.getPrincipal();
            return ResponseEntity.ok(secureUser.getUser().getNickname());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}