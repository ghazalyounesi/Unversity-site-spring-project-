// src/main/java/com/example/demo/dto/ListDto/StudentListDto.java
package com.example.demo.dto.ListDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentListDto {
    private Long id;
    private String name;
    private String studentId;

    public StudentListDto(Long id, String name, String studentId) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
    }
}
