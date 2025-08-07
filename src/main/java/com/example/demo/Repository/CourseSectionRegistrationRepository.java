package com.example.demo.Repository;

import com.example.demo.model.dto.ListDto.TermSummaryDto;
import com.example.demo.model.entity.CourseSectionRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseSectionRegistrationRepository extends JpaRepository<CourseSectionRegistration, Long> {

    long countByCourseSectionId(Long courseSectionId);

    List<CourseSectionRegistration> findByCourseSectionId(Long courseSectionId);

    boolean existsByStudentIdAndCourseSectionId(Long studentId, Long courseSectionId);

    Optional<CourseSectionRegistration> findByCourseSectionIdAndStudentId(Long courseSectionId, Long studentId);

    List<CourseSectionRegistration> findByStudentIdAndCourseSectionIdIn(Long studentId, List<Long> courseSectionIds);

    @Query("SELECT new com.example.demo.model.dto.ListDto.TermSummaryDto(t.id, t.title, " +
            "CAST(SUM(csr.score * c.units) AS DOUBLE) / CAST(SUM(c.units) AS DOUBLE)) " +
            "FROM CourseSectionRegistration csr " +
            "JOIN CourseSection cs ON csr.courseSectionId = cs.id " +
            "JOIN Term t ON cs.termId = t.id " +
            "JOIN Course c ON cs.courseId = c.id " +
            "WHERE csr.studentId = :studentId AND csr.score IS NOT NULL " +
            "GROUP BY t.id, t.title")
    List<TermSummaryDto> findTermGpaSummaryByStudentId(Long studentId);

    @Query("SELECT CAST(SUM(csr.score * c.units) AS DOUBLE) / CAST(SUM(c.units) AS DOUBLE) " +
            "FROM CourseSectionRegistration csr " +
            "JOIN CourseSection cs ON csr.courseSectionId = cs.id " +
            "JOIN Course c ON cs.courseId = c.id " +
            "WHERE csr.studentId = :studentId AND csr.score IS NOT NULL")
    Double findOverallGpaByStudentId(Long studentId);
}