package com.example.demo.model.dto.ProfileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
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