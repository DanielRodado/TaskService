package com.example.taskservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public Mono<ResponseEntity<String>> handleTaskNotFoundException(TaskNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    @ExceptionHandler(InvalidTaskException.class)
    public Mono<ResponseEntity<String>> handleInvalidTaskException(InvalidTaskException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage()));
    }

    @ExceptionHandler(TaskNotOwnedByUserException.class)
    public Mono<ResponseEntity<String>> handleTaskNotOwnedByUserException(TaskNotOwnedByUserException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage()));
    }

}
