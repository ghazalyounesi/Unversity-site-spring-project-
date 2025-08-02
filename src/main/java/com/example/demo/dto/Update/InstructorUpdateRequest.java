package com.example.demo.dto.Update;

import com.example.demo.entity.Instructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorUpdateRequest {
    // Fields from User that can be updated
    private String name;
    private String phone;
    private String password;

    // Fields from Instructor that can be updated
    private Instructor.Rank rank;
}