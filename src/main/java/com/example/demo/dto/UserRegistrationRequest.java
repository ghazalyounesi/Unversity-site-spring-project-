// src/main/java/com/example/demo/dto/UserRegistrationRequest.java
package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String nationalId;
}