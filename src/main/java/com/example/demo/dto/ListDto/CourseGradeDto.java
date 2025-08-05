package com.example.demo.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseGradeDto {
    private Long courseSectionId;
    private String courseTitle;
    private int courseUnits;
    private String instructorName;
    private Double score;
}
