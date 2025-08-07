package com.example.demo.model.dto.ProfileDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StaffProfileDto {
    private String name;
    private String username;
    private String phone;
    private String personnelId;

}