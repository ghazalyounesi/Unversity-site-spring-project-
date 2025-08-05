package com.example.demo.Service;

import com.example.demo.dto.ProfileDto.CourseDto;
import com.example.demo.entity.Course;
import com.example.demo.Repasitory.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course createCourse(CourseDto dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setUnits(dto.getUnits());
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }

    public Course updateCourse(Long id, CourseDto dto) {
        Course existingCourse = getCourseById(id);
        existingCourse.setTitle(dto.getTitle());
        existingCourse.setUnits(dto.getUnits());
        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
