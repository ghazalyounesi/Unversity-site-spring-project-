package com.example.demo.model.dto.ListDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class BulkGradeRequestDto {
    private List<GradeEntryDto> grades;
}
