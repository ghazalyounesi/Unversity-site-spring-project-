package com.example.demo.specification;

import com.example.demo.entity.CourseSection;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class CourseSectionSpecification {

    public static Specification<CourseSection> hasTermId(Long termId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("termId"), termId);
    }

    public static Specification<CourseSection> hasCourseIds(List<Long> courseIds) {
        return (root, query, criteriaBuilder) ->
                root.get("courseId").in(courseIds);
    }

    public static Specification<CourseSection> hasInstructorIds(List<Long> instructorIds) {
        return (root, query, criteriaBuilder) ->
                root.get("instructorId").in(instructorIds);
    }
}