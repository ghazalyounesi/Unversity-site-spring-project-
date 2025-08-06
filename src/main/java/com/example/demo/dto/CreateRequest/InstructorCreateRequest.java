package com.example.demo.dto.CreateRequest;

import com.example.demo.entity.Instructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorCreateRequest {
    private String username;
    private Instructor.Rank rank;
}