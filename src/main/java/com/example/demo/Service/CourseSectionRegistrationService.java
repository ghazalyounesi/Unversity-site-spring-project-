package com.example.demo.Service;

import com.example.demo.Repasitory.CourseSectionRegistrationRepository;
import com.example.demo.Repasitory.StudentRepository;
import com.example.demo.Repasitory.UserRepository;
import com.example.demo.dto.CreateRequest.CourseSectionRegistrationRequestDto;
import com.example.demo.entity.CourseSectionRegistration;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseSectionRegistrationService {

    private final CourseSectionRegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public CourseSectionRegistration createRegistration(CourseSectionRegistrationRequestDto request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));

        Student student = studentRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("The current user is not a student."));

        boolean alreadyEnrolled = registrationRepository.existsByStudentIdAndCourseSectionId(student.getId(), request.getCourseSectionId());
        if (alreadyEnrolled) {
            throw new IllegalStateException("Student is already enrolled in this course section.");
        }

        CourseSectionRegistration registration = new CourseSectionRegistration();
        registration.setStudentId(student.getId());
        registration.setCourseSectionId(request.getCourseSectionId());

        return registrationRepository.save(registration);
    }
}