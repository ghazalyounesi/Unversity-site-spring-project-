package com.example.demo.dto.Update;

import com.example.demo.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateRequest {
    private String name;
    private String phone;
    private String password;
    private Student.Degree degree;
}