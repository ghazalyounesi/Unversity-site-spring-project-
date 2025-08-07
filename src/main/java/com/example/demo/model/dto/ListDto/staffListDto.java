package com.example.demo.model.dto.ListDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
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
