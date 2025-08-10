package com.example.demo.controller;

import com.example.demo.Service.CourseSectionService;
import com.example.demo.exception.CourseSectionDeletionException;
import com.example.demo.model.dto.CreateRequest.CourseSectionCreateDto;
import com.example.demo.model.dto.ListDto.CourseSectionListDto;
import com.example.demo.model.dto.ListDto.EnrolledStudentDto;
import com.example.demo.model.dto.ProfileDto.CourseSectionResponseDto;
import com.example.demo.model.dto.Update.CourseSectionUpdateRequestDto;
import com.example.demo.model.entity.CourseSection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.exception.UserNotFoundCheckedException;
import com.example.demo.exception.UserNotInstructorException;
import com.example.demo.exception.InvalidCourseSectionDataException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/course-sections")
@RequiredArgsConstructor
public class CourseSectionController {

    private final CourseSectionService courseSectionService;

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<CourseSection> createCourseSection(@RequestBody CourseSectionCreateDto dto)
            throws UserNotFoundCheckedException, UserNotInstructorException, InvalidCourseSectionDataException {

        return ResponseEntity.ok(courseSectionService.createCourseSection(dto));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CourseSectionListDto>> getCourseSections(
            @RequestParam Long termId,
            @RequestParam(required = false) String courseTitle,
            @RequestParam(required = false) String instructorName,
            Pageable pageable) {
        return ResponseEntity.ok(courseSectionService.getAllCourseSections(termId, courseTitle, instructorName, pageable));
    }

    @GetMapping("/{id}/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'INSTRUCTOR')")
    public ResponseEntity<List<EnrolledStudentDto>> getEnrolledStudents(@PathVariable Long id) {
        List<EnrolledStudentDto> students = courseSectionService.getEnrolledStudents(id);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CourseSectionResponseDto> getCourseSectionById(@PathVariable Long id) {
        return ResponseEntity.ok(courseSectionService.getCourseSectionById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @courseSectionSecurityService.isOwner(authentication, #id)")
    public ResponseEntity<Void> deleteCourseSection(@PathVariable Long id)throws CourseSectionDeletionException {
        courseSectionService.deleteCourseSection(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @courseSectionSecurityService.isOwner(authentication, #id)")
    public ResponseEntity<CourseSectionResponseDto> updateCourseSection(@PathVariable Long id, @RequestBody CourseSectionUpdateRequestDto dto) {
        CourseSectionResponseDto updatedSection = courseSectionService.updateCourseSection(id, dto);
        return ResponseEntity.ok(updatedSection);
    }
}