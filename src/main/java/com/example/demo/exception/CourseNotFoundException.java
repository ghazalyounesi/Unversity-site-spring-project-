package com.example.demo.exception;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
