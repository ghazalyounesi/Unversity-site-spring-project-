package com.example.demo.controller;

import com.example.demo.Service.GradingService;
import com.example.demo.dto.ListDto.BulkGradeRequestDto;
import com.example.demo.dto.ListDto.GradeEntryDto;
import com.example.demo.entity.CourseSectionRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-sections/{courseSectionId}/grades")
@RequiredArgsConstructor
public class GradingController {

    private final GradingService gradingService;

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR') and @courseSectionSecurityService.isOwner(authentication, #courseSectionId)")
    public ResponseEntity<CourseSectionRegistration> gradeStudent(
            @PathVariable Long courseSectionId,
            @RequestBody GradeEntryDto gradeDto) {

        CourseSectionRegistration updatedRegistration = gradingService.gradeStudent(
                courseSectionId,
                gradeDto.getStudentId(),
                gradeDto.getScore()
        );
        return ResponseEntity.ok(updatedRegistration);
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('INSTRUCTOR') and @courseSectionSecurityService.isOwner(authentication, #courseSectionId)")
    public ResponseEntity<Void> gradeStudentsBulk(
            @PathVariable Long courseSectionId,
            @RequestBody BulkGradeRequestDto bulkGradeDto) {

        gradingService.gradeStudentsBulk(courseSectionId, bulkGradeDto);
        return ResponseEntity.ok().build();
    }
}