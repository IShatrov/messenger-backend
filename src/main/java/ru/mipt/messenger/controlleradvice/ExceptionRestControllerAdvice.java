package ru.mipt.messenger.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mipt.messenger.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class ExceptionRestControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // Use the same handler for most exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> dataIntegrityViolationExceptionHandler(Exception ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
