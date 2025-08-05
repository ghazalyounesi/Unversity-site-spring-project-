package com.example.demo.dto.ListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TermSummaryDto {
    private Long termId;
    private String termTitle;
    private double termGpa;
}
