package com.example.demo.model.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
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
