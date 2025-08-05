package com.example.demo.Service;

import com.example.demo.dto.ListDto.BulkGradeRequestDto;
import com.example.demo.dto.ListDto.GradeEntryDto;
import com.example.demo.entity.CourseSectionRegistration;
import com.example.demo.Repasitory.CourseSectionRegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradingService {

    private final CourseSectionRegistrationRepository registrationRepository;

    @Transactional
    public CourseSectionRegistration gradeStudent(Long courseSectionId, Long studentId, Double score) {
        CourseSectionRegistration registration = registrationRepository.findByCourseSectionIdAndStudentId(courseSectionId, studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " is not enrolled in this course section."));

        registration.setScore(score);
        return registrationRepository.save(registration);
    }

    @Transactional
    public void gradeStudentsBulk(Long courseSectionId, BulkGradeRequestDto request) {
        for (GradeEntryDto gradeEntry : request.getGrades()) {
            gradeStudent(courseSectionId, gradeEntry.getStudentId(), gradeEntry.getScore());
        }
    }
}