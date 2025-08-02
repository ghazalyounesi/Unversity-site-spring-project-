// src/main/java/com/example/demo/dto/InstructorCreateRequest.java
package com.example.demo.dto.CreateRequest;

import com.example.demo.entity.Instructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorCreateRequest {
    // The username of the user who will become an instructor
    private String username;

    // Instructor-specific information
    private Instructor.Rank rank;
}