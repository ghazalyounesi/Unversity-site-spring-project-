// src/main/java/com/example/demo/entity/Staff.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId; // Foreign key to User entity
    @Column(name = "personnel_id", unique = true)
    private String personnelld;
    public Staff() {}
    public Staff(Long userId,String personnelld) {
        this.personnelld = personnelld;
        this.userId = userId;
    }
}