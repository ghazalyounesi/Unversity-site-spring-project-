package com.example.demo.exception;

public class UserNotFoundCheckedException extends Exception {
    public UserNotFoundCheckedException(String message) {
        super(message);
    }
}
