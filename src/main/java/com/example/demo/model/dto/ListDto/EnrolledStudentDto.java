package com.example.demo.model.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class EnrolledStudentDto {
    private Long studentEntityId;
    private String name;
    private String studentCode;
    private Double score;
}
