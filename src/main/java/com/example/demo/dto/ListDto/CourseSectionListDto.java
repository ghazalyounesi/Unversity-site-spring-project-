package com.example.demo.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseSectionListDto {
    private Long id;
    private String courseTitle;
    private String instructorName;
}