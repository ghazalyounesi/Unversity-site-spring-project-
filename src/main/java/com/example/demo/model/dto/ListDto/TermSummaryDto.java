package com.example.demo.model.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TermSummaryDto {
    private Long termId;
    private String termTitle;
    private double termGpa;
}
