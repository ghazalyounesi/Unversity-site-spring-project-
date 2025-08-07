package com.example.demo.Repository;

import com.example.demo.model.entity.CourseSection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseSectionRepository extends JpaRepository<CourseSection, Long>, JpaSpecificationExecutor<CourseSection> {
    List<CourseSection> findAllByTermId(Long termId);

    @Modifying
    @Query("""
                DELETE FROM CourseSection cs
                WHERE cs.id = :id AND NOT EXISTS (
                    SELECT 1 FROM CourseSectionRegistration csr WHERE csr.courseSectionId = :id
                )
            """)
    int deleteCourseSectionIfNoRegistrations(@Param("id") Long id);

    @Query("""
                SELECT cs FROM CourseSection cs
                JOIN Course c ON cs.courseId = c.id
                JOIN Instructor i ON cs.instructorId = i.id
                JOIN User u ON i.userId = u.id
                WHERE (:termId IS NULL OR cs.termId = :termId)
                  AND (:courseTitle IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :courseTitle, '%')))
                  AND (:instructorName IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :instructorName, '%')))
            """)
    Page<CourseSection> findByFilters(
            @Param("termId") Long termId,
            @Param("courseTitle") String courseTitle,
            @Param("instructorName") String instructorName,
            Pageable pageable);
}