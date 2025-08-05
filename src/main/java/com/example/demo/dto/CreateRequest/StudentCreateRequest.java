// src/main/java/com/example/demo/dto/StudentCreateRequest.java
package com.example.demo.dto.CreateRequest;

import com.example.demo.entity.Student;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class StudentCreateRequest {
    private String username;
    private String studentId;
    private Student.Degree degree;
    private Date startDate;
}