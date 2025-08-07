package com.example.demo.model.dto.Update;

import com.example.demo.model.entity.Student;
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