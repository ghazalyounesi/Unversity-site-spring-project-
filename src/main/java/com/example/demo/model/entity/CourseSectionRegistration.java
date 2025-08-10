package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
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

    public static CourseSectionRegistration of(Long studentId, Long courseSectionId) {
        CourseSectionRegistration registration = new CourseSectionRegistration();
        registration.setStudentId(studentId);
        registration.setCourseSectionId(courseSectionId);
        return registration;
    }
}