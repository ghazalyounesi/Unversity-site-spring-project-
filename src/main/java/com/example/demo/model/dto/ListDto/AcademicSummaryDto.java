package com.example.demo.model.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AcademicSummaryDto {
    private double overallGpa;
    private List<TermSummaryDto> termSummaries;
}
