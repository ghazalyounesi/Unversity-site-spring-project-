package com.example.demo.dto.ProfileDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseSectionResponseDto {
    private Long id;
    private String courseTitle;
    private String termTitle;
    private String instructorName;
    private int courseUnits;
    private long studentCount;
}