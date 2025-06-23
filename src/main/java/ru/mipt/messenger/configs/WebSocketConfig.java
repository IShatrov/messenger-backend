package ru.mipt.messenger.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Для подписки клиентов
        config.setApplicationDestinationPrefixes("/app"); // Для отправки сообщений на сервер
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new AuthHandshakeInterceptor())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    private static class AuthHandshakeInterceptor implements HandshakeInterceptor {
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            if (request instanceof org.springframework.http.server.ServletServerHttpRequest servletRequest) {
                HttpSession session = servletRequest.getServletRequest().getSession(false);
                if (session == null) {
                    System.out.println("[WS] No HTTP session found");
                    return false;
                }
                SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
                if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
                    System.out.println("[WS] No authenticated user in session");
                    return false;
                }
                System.out.println("[WS] Authenticated user: " + context.getAuthentication().getName());
                return true;
            }
            System.out.println("[WS] Not a servlet request");
            return false;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
            // no-op
        }
    }
} 