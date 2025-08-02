// src/main/java/com/example/demo/entity/Instructor.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "instructor")
public class Instructor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // Foreign key to User entity

    public enum Rank {
        ASSISTANT, ASSOCIATE, FULL
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "rank")
    private Rank rank;

    public Instructor(Long userId, Rank rank) {
        this.rank = rank;
        this.userId = userId;
    }
    public Instructor() {}
}