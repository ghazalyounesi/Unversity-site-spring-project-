package com.example.demo.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AcademicSummaryDto {
    private double overallGpa;
    private List<TermSummaryDto> termSummaries;
}
