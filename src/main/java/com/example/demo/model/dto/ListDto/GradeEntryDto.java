package com.example.demo.model.dto.ListDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GradeEntryDto {
    private Long studentId;
    private Double score;

    public static GradeEntryDto of(Long studentId, Double score) {
        return GradeEntryDto.builder()
                .studentId(studentId)
                .score(score)
                .build();
    }
}
