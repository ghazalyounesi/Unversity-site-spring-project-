// src/main/java/com/example/demo/dto/StaffCreateRequest.java
package com.example.demo.dto.CreateRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffCreateRequest {
    // The username of the user who will become a staff member
    private String username;

    // Staff-specific information
    private String personnelId;
}