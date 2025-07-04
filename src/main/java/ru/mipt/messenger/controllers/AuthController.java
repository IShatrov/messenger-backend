package ru.mipt.messenger.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.mipt.messenger.models.SecureUser;
import ru.mipt.messenger.services.SessionInfoService;
import ru.mipt.messenger.dto.UserDto;
import ru.mipt.messenger.services.UserService;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionInfoService sessionInfoService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserDto login(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            System.out.println("[LOGIN] No Authorization header provided");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        String base64Credentials = authHeader.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        String[] values = credentials.split(":", 2);
        String username = values[0];
        String password = values[1];
        System.out.println("[LOGIN] Authorization header detected. Username: " + username);
        try {
            System.out.println("[LOGIN] Attempting authentication for: " + username);
            UsernamePasswordAuthenticationToken authReq =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication auth = authenticationManager.authenticate(authReq);
            System.out.println("[LOGIN] Authentication successful for: " + username);
            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext context = SecurityContextHolder.getContext();
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);
            User user = userRepository.findUserByNickname(username).orElseThrow();
            System.out.println("[LOGIN] Returning user: " + user.getNickname());
            return new UserDto(user);
        } catch (Exception e) {
            System.out.println("[LOGIN] Authentication failed for: " + username + ". Reason: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody User newUser) {
        userService.createUser(newUser);
        User user = userRepository.findUserByNickname(newUser.getNickname()).orElseThrow();
        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping("/session/info")
    public SessionInfoService.SessionInfoResponse getSessionInfo(@AuthenticationPrincipal SecureUser secureUser) {
        return sessionInfoService.getSessionInfo(secureUser.getUser().getUserId());
    }
}
