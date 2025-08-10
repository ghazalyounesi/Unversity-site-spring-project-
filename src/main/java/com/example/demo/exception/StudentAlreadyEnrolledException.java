package com.example.demo.exception;

public class StudentAlreadyEnrolledException extends Exception {
    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }
}
