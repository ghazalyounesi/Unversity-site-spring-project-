package com.example.demo.exception;

public class EntityAlreadyExistsException extends Exception {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
