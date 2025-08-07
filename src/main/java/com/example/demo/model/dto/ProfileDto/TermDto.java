package com.example.demo.model.dto.ProfileDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TermDto {
    private String title;
    private boolean open;
}
