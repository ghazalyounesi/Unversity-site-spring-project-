package com.example.demo.model.dto.ListDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
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
