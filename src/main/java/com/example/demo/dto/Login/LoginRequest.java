// src/main/java/com/example/demo/dto/LoginRequest.java
package com.example.demo.dto.Login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}