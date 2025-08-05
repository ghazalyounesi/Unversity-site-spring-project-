package com.example.demo.dto.Update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffUpdateRequest {
    private String name;
    private String phone;
    private String password;
}