package com.example.demo.exception;

public class UserNotStudentException extends Exception {
    public UserNotStudentException(String message) {
        super(message);
    }
}
