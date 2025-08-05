package com.example.demo.Service;

import com.example.demo.entity.CourseSection;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.User;
import com.example.demo.Repasitory.CourseSectionRepository;
import com.example.demo.Repasitory.InstructorRepository;
import com.example.demo.Repasitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("courseSectionSecurityService")
@RequiredArgsConstructor
public class CourseSectionSecurityService {

    private final CourseSectionRepository courseSectionRepository;
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;

    public boolean isOwner(Authentication authentication, Long courseSectionId) {

        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username).orElse(null);
        if (currentUser == null) {
            return false;
        }

        Instructor currentInstructor = instructorRepository.findByUserId(currentUser.getId()).orElse(null);
        if (currentInstructor == null) {
            return false;
        }
        CourseSection section = courseSectionRepository.findById(courseSectionId).orElse(null);
        if (section == null) {
            return false;
        }

        return section.getInstructorId().equals(currentInstructor.getId());
    }
}