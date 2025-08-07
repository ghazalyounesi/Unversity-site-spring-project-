package com.example.demo.model.dto.CreateRequest;

import com.example.demo.model.entity.Instructor;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InstructorCreateRequest {
    private String username;
    private Instructor.Rank rank;
}