package com.example.demo.model.dto.CreateRequest;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CourseSectionCreateDto {
    private Long termId;
    private Long courseId;
}