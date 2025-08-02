package com.example.demo.dto.Update;

import com.example.demo.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateRequest {
    // Fields from User that can be updated
    private String name;
    private String phone;
    private String password;

    // Fields from Student that can be updated
    private Student.Degree degree;
}