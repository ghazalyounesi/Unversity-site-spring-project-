package com.example.demo.model.dto.ProfileDto;

import com.example.demo.model.entity.Instructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InstructorProfileDto {
    private String name;
    private String username;
    private String phone;
    private Instructor.Rank rank;

}