package com.example.demo.dto.Update;

import com.example.demo.entity.Instructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorUpdateRequest {
    private String name;
    private String phone;
    private String password;
    private Instructor.Rank rank;
}