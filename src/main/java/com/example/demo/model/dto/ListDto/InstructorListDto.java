package com.example.demo.model.dto.ListDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InstructorListDto {
    private Long id;
    private String name;

    public InstructorListDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
