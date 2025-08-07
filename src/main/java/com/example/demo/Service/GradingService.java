package com.example.demo.Service;

import com.example.demo.Repository.CourseSectionRegistrationRepository;
import com.example.demo.model.dto.ListDto.BulkGradeRequestDto;
import com.example.demo.model.entity.CourseSectionRegistration;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        request.getGrades().forEach(gradeEntry ->
                gradeStudent(courseSectionId, gradeEntry.getStudentId(), gradeEntry.getScore())
        );
    }
}