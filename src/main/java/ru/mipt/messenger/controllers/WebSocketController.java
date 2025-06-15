package ru.mipt.messenger.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/echo")
    @SendTo("/topic/echo")
    public String echo(String message) {
        System.out.println("[WS] Echo received: " + message);
        return message;
    }
} 