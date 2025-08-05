package com.example.demo.dto.ListDto;

import lombok.Getter;
import lombok.Setter;

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
