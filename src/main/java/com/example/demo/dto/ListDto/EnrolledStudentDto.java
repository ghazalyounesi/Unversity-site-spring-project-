package com.example.demo.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnrolledStudentDto {
    private Long studentEntityId;
    private String name;
    private String studentCode;
    private Double score;
}
