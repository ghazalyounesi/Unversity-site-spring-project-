package com.example.demo.controller;

import com.example.demo.Service.StudentGradeService;
import com.example.demo.dto.ListDto.AcademicSummaryDto;
import com.example.demo.dto.ListDto.TermGradesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student/grades")
@RequiredArgsConstructor
public class StudentGradeController {

    private final StudentGradeService studentGradeService;

    @GetMapping("/summary")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AcademicSummaryDto> getAcademicSummary() {
        return ResponseEntity.ok(studentGradeService.getAcademicSummary());
    }

    @GetMapping("/term/{termId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TermGradesDto> getGradesForTerm(@PathVariable Long termId) {
        return ResponseEntity.ok(studentGradeService.getTermGrades(termId));
    }
}