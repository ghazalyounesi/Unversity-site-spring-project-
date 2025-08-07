package com.example.demo.model.dto.ListDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class TermListDto {
    private Long id;
    private String title;
}