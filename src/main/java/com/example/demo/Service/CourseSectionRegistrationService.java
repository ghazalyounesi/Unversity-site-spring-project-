package com.example.demo.Service;

import com.example.demo.Repository.CourseSectionRegistrationRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.StudentAlreadyEnrolledException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.dto.CreateRequest.CourseSectionRegistrationRequestDto;
import com.example.demo.model.entity.CourseSectionRegistration;
import com.example.demo.model.entity.Student;
import com.example.demo.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseSectionRegistrationService {

    private final CourseSectionRegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public CourseSectionRegistration createRegistration(CourseSectionRegistrationRequestDto request) throws ResourceNotFoundException, UnauthorizedException, StudentAlreadyEnrolledException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        Student student = studentRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new UnauthorizedException("The current user is not a student."));

        boolean alreadyEnrolled = registrationRepository.existsByStudentIdAndCourseSectionId(student.getId(), request.getCourseSectionId());
        if (alreadyEnrolled) {
            throw new StudentAlreadyEnrolledException("Student is already enrolled in this course section.");
        }

        CourseSectionRegistration registration =
                CourseSectionRegistration.of(student.getId(), request.getCourseSectionId());

        return registrationRepository.save(registration);
    }
}