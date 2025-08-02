// src/main/java/com/example/demo/entity/CourseSectionRegistration.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course_section_registrations")
public class CourseSectionRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Long courseSectionId;

    @Column(nullable = true)
    private Double score;
}