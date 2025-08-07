package com.example.demo.model.dto.CreateRequest;

import com.example.demo.model.entity.Student;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StudentCreateRequest {
    private String username;
    private String studentId;
    private Student.Degree degree;
    private Date startDate;
}