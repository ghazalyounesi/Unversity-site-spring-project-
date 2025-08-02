// src/main/java/com/example/demo/dto/InstructorProfileDto.java
package com.example.demo.dto.ProfileDto;

import com.example.demo.entity.Instructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorProfileDto {
    // Combined Info
    private String name;
    private String username;
    private String phone;
    private Instructor.Rank rank;

}