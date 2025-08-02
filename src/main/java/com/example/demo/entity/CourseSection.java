// src/main/java/com/example/demo/entity/CourseSection.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course_sections")
public class CourseSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long termId;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private Long instructorId;
}