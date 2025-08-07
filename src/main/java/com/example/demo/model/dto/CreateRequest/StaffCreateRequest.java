package com.example.demo.model.dto.CreateRequest;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StaffCreateRequest {
    private String username;
    private String personnelId;
}