package com.example.demo.Service;

import com.example.demo.Repasitory.*;
import com.example.demo.dto.CreateRequest.CourseSectionCreateDto;
import com.example.demo.dto.ListDto.CourseSectionListDto;
import com.example.demo.dto.ListDto.EnrolledStudentDto;
import com.example.demo.dto.ProfileDto.CourseSectionResponseDto;
import com.example.demo.dto.Update.CourseSectionUpdateRequestDto;
import com.example.demo.entity.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CourseSectionService {

    private final CourseSectionRepository courseSectionRepository;
    private final CourseSectionRegistrationRepository registrationRepository;
    private final TermRepository termRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public CourseSection createCourseSection(CourseSectionCreateDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        Instructor instructor = instructorRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new AccessDeniedException("Current user is not an instructor"));

        if (dto.getTermId() == null || dto.getCourseId() == null) {
            throw new IllegalArgumentException("Term ID and Course ID must not be null");
        }

        CourseSection section = CourseSection.builder()
                .termId(dto.getTermId())
                .courseId(dto.getCourseId())
                .instructorId(instructor.getId())
                .build();


        return courseSectionRepository.save(section);
    }

    public CourseSectionResponseDto getCourseSectionById(Long id) {
        CourseSection section = courseSectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course Section not found"));

        long studentCount = registrationRepository.countByCourseSectionId(id);

        return mapToResponseDto(section, studentCount);
    }

    @Transactional
    public void deleteCourseSection(Long id) {
        int deletedCount = courseSectionRepository.deleteCourseSectionIfNoRegistrations(id);
        if (deletedCount == 0) {
            throw new IllegalStateException("Cannot delete course section with registered students or it does not exist.");
        }
    }

    public Page<CourseSectionListDto> getAllCourseSections(Long termId, String courseTitle, String instructorName, Pageable pageable) {
        Page<CourseSection> sectionsPage = courseSectionRepository.findByFilters(
                termId,
                (courseTitle != null && !courseTitle.isEmpty()) ? courseTitle : null,
                (instructorName != null && !instructorName.isEmpty()) ? instructorName : null,
                pageable
        );
        return sectionsPage.map(this::mapToListDto);
    }

    @Transactional
    public CourseSectionResponseDto updateCourseSection(Long id, CourseSectionUpdateRequestDto dto) {
        CourseSection section = courseSectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course Section not found with id: " + id));

        if (dto.getInstructorId() != null) {
            instructorRepository.findById(dto.getInstructorId())
                    .orElseThrow(() -> new EntityNotFoundException("New instructor not found with id: " + dto.getInstructorId()));
            section.setInstructorId(dto.getInstructorId());
        }

        CourseSection updatedSection = courseSectionRepository.save(section);
        long studentCount = registrationRepository.countByCourseSectionId(updatedSection.getId());
        return mapToResponseDto(updatedSection, studentCount);
    }


    public List<EnrolledStudentDto> getEnrolledStudents(Long courseSectionId) {
        List<CourseSectionRegistration> registrations = registrationRepository.findByCourseSectionId(courseSectionId);

        return registrations.stream().map(registration -> {
            Student student = studentRepository.findById(registration.getStudentId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found for registration"));
            User user = userRepository.findById(student.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found for student"));

            return new EnrolledStudentDto(
                    student.getId(),
                    user.getName(),
                    student.getStudentid(),
                    registration.getScore()
            );
        }).collect(Collectors.toList());
    }

    private CourseSectionResponseDto mapToResponseDto(CourseSection section, long studentCount) {
        Course course = courseRepository.findById(section.getCourseId()).orElse(new Course());
        Term term = termRepository.findById(section.getTermId()).orElse(new Term());
        Instructor instructor = instructorRepository.findById(section.getInstructorId()).orElse(new Instructor());
        User instructorUser = userRepository.findById(instructor.getUserId()).orElse(new User());

        return new CourseSectionResponseDto(
                section.getId(),
                course.getTitle(),
                term.getTitle(),
                instructorUser.getName(),
                course.getUnits(),
                studentCount
        );
    }

    private CourseSectionListDto mapToListDto(CourseSection section) {
        String courseTitle = courseRepository.findById(section.getCourseId())
                .map(Course::getTitle)
                .orElse("N/A");

        String instructorName = instructorRepository.findById(section.getInstructorId())
                .flatMap(instructor -> userRepository.findById(instructor.getUserId()))
                .map(User::getName)
                .orElse("N/A");

        return new CourseSectionListDto(
                section.getId(),
                courseTitle,
                instructorName
        );
    }

}
