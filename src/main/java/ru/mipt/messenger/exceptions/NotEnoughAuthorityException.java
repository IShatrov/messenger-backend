package ru.mipt.messenger.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NotEnoughAuthorityException extends AuthenticationException {
    public NotEnoughAuthorityException(String message) {
        super(message);
    }
}
