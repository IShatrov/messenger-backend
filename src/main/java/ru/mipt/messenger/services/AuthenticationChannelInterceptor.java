package ru.mipt.messenger.services;

import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mipt.messenger.models.SecureUser;

import java.util.Base64;

@Component
@AllArgsConstructor
public class AuthenticationChannelInterceptor implements ChannelInterceptor {
    private DatabaseUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    // lots of antipatterns
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        String authHeader = accessor.getFirstNativeHeader("Authorization");

        // authHeader can be null despite IDE saying otherwise
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring(6);
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            try {
                SecureUser userDetails = userDetailsService.loadUserByUsername(username);

                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                    throw new RuntimeException("Invalid password");
                }

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails,
                        userDetails.getAuthorities()
                );

                accessor.setUser(auth);

            } catch (Exception e) {
                throw new RuntimeException("Authentication failed: " + e.getMessage());
            }
        }

        return message;
    }
}

