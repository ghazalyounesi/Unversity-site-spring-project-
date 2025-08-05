package com.example.demo.dto.CreateRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseSectionCreateDto {
    private Long termId;
    private Long courseId;
}