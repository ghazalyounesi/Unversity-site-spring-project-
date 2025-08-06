package com.example.demo.dto.ProfileDto;

import com.example.demo.entity.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentProfileDto {
    private String name;
    private String username;
    private String phone;
    private String studentId;
    private Student.Degree degree;
    private Date startDate;

}