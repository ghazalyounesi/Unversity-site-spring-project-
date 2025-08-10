package com.example.demo.Service;

import com.example.demo.Repository.*;
import com.example.demo.model.dto.ListDto.AcademicSummaryDto;
import com.example.demo.model.dto.ListDto.CourseGradeDto;
import com.example.demo.model.dto.ListDto.TermGradesDto;
import com.example.demo.model.dto.ListDto.TermSummaryDto;
import com.example.demo.model.entity.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.demo.exception.UserNotFoundCheckedException;
import com.example.demo.exception.UserNotStudentException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentGradeService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final CourseSectionRegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public TermGradesDto getTermGrades(Long termId) throws UserNotFoundCheckedException, UserNotStudentException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundCheckedException("User not found"));
        Student student = studentRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new UserNotStudentException("Current user is not a student"));

        List<Long> sectionIdsInTerm = courseSectionRepository.findAllByTermId(termId).stream()
                .map(CourseSection::getId)
                .collect(Collectors.toList());

        List<CourseSectionRegistration> registrations = registrationRepository.findByStudentIdAndCourseSectionIdIn(student.getId(), sectionIdsInTerm);

        List<CourseGradeDto> courseGrades = new ArrayList<>();
        double totalWeightedScore = 0;
        int totalUnits = 0;

        for (CourseSectionRegistration reg : registrations) {
            CourseSection section = courseSectionRepository.findById(reg.getCourseSectionId()).orElseThrow();
            Course course = courseRepository.findById(section.getCourseId()).orElseThrow();
            Instructor instructor = instructorRepository.findById(section.getInstructorId()).orElseThrow();
            User instructorUser = userRepository.findById(instructor.getUserId()).orElseThrow();

            if (reg.getScore() != null) {
                totalWeightedScore += reg.getScore() * course.getUnits();
                totalUnits += course.getUnits();
            }

            courseGrades.add(new CourseGradeDto(
                    section.getId(),
                    course.getTitle(),
                    course.getUnits(),
                    instructorUser.getName(),
                    reg.getScore()
            ));
        }

        double termGpa = (totalUnits > 0) ? totalWeightedScore / totalUnits : 0.0;

        return new TermGradesDto(termGpa, courseGrades);
    }

    public AcademicSummaryDto getAcademicSummary() throws UserNotFoundCheckedException, UserNotStudentException{
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundCheckedException("User not found"));
        Student student = studentRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new UserNotStudentException("Current user is not a student"));

        List<TermSummaryDto> termSummaries = registrationRepository.findTermGpaSummaryByStudentId(student.getId());

        Double overallGpa = registrationRepository.findOverallGpaByStudentId(student.getId());

        return new AcademicSummaryDto(overallGpa != null ? overallGpa : 0.0, termSummaries);
    }
}