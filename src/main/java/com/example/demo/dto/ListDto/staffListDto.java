package com.example.demo.dto.ListDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class staffListDto {
    private Long id;
    private String name;
    private String personnelId;

    public staffListDto(Long id, String name, String personnelId) {
        this.id = id;
        this.name = name;
        this.personnelId = personnelId;
    }
}
