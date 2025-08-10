package com.example.demo.exception;

public class UserNotAuthenticatedException extends Exception {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
