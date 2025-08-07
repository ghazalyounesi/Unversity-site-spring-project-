package com.example.demo.model.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CourseSectionListDto {
    private Long id;
    private String courseTitle;
    private String instructorName;
}