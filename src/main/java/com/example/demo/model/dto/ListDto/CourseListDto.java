package com.example.demo.model.dto.ListDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CourseListDto {
    private Long id;
    private String title;
}
