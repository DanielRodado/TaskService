package com.example.taskservice.exceptions;

public class TaskNotOwnedByUserException extends RuntimeException {

    public TaskNotOwnedByUserException(String message) {
        super(message);
    }

}
