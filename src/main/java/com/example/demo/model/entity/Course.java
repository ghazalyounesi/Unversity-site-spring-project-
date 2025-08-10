package com.example.demo.model.entity;

import com.example.demo.model.dto.ProfileDto.CourseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int units;

    public static Course fromDto(CourseDto dto) {
        Course course = Course.builder()
                .title(dto.getTitle())
                .units(dto.getUnits())
                .build();

        return course;
    }
    public static Course createCourse(String title, int units) {
        Course course = new Course();
        course.setTitle(title);
        course.setUnits(units);
        return course;
    }

}