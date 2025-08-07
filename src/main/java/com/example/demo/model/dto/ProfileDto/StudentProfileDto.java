package com.example.demo.model.dto.ProfileDto;

import com.example.demo.model.entity.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
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