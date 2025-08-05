package com.example.demo.dto.ListDto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class BulkGradeRequestDto {
    private List<GradeEntryDto> grades;
}
