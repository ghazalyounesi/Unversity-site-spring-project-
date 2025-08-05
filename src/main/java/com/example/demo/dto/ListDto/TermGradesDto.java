package com.example.demo.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TermGradesDto {
    private double termGpa;
    private List<CourseGradeDto> courses;
}