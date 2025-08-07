package com.example.demo.Service;

import com.example.demo.Repository.CourseSectionRepository;
import com.example.demo.Repository.InstructorRepository;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseSectionSecurityService {

    private final CourseSectionRepository courseSectionRepository;
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;

    public boolean isOwner(Authentication authentication, Long courseSectionId) {
        return userRepository.findByUsername(authentication.getName())
                .flatMap(user -> instructorRepository.findByUserId(user.getId()))
                .flatMap(instructor -> courseSectionRepository.findById(courseSectionId)
                        .filter(section -> section.getInstructorId().equals(instructor.getId())))
                .isPresent();
    }

}