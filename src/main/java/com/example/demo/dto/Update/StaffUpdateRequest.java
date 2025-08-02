package com.example.demo.dto.Update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffUpdateRequest {
    // Fields from User that can be updated
    private String name;
    private String phone;
    private String password;
}