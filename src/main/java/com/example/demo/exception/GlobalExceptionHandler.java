package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(UnauthorizedException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundCheckedException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundCheckedException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<Map<String, String>> handleUserNotAuthenticated(UserNotAuthenticatedException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEntityAlreadyExists(EntityAlreadyExistsException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<Map<String, String>> handleStudentAlreadyEnrolled(StudentAlreadyEnrolledException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotInstructorException.class)
    public ResponseEntity<Map<String, String>> handleUserNotInstructor(UserNotInstructorException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCourseSectionDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCourseSectionData(InvalidCourseSectionDataException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCourseNotFound(CourseNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentNotEnrolledException.class)
    public ResponseEntity<Map<String, String>> handleStudentNotEnrolled(StudentNotEnrolledException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserNotStudentException.class)
    public ResponseEntity<Map<String, String>> handleUserNotStudent(UserNotStudentException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleStaffNotFound(StaffNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssociatedUserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAssociatedUserNotFound(AssociatedUserNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseSectionDeletionException.class)
    public ResponseEntity<Map<String, String>> handleCourseSectionDeletion(CourseSectionDeletionException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
