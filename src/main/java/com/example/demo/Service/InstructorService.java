package com.example.demo.Service;

import com.example.demo.Repository.InstructorRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.UserNotFoundCheckedException;
import com.example.demo.model.dto.CreateRequest.InstructorCreateRequest;
import com.example.demo.model.dto.ListDto.InstructorListDto;
import com.example.demo.model.dto.ProfileDto.InstructorProfileDto;
import com.example.demo.model.dto.Update.InstructorUpdateRequest;
import com.example.demo.model.entity.Instructor;
import com.example.demo.model.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InstructorService(InstructorRepository instructorRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public InstructorProfileDto createInstructor(InstructorCreateRequest request) throws UserNotFoundCheckedException {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundCheckedException(
                        "User not found with username: " + request.getUsername()));
        Instructor instructor = Instructor.builder()
                .userId(user.getId())
                .rank(request.getRank())
                .build();

        Instructor savedInstructor = instructorRepository.save(instructor);

        user.getRoles().add("INSTRUCTOR");
        user.setActive(true);
        userRepository.save(user);

        return mapToProfileDto(savedInstructor, user);
    }

    @Transactional
    public InstructorProfileDto updateInstructor(Long id, InstructorUpdateRequest request) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id: " + id));

        User user = userRepository.findById(instructor.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Associated user not found for instructor id: " + instructor.getId()));

        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            user.setPhone(request.getPhone());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRank() != null) {
            instructor.setRank(request.getRank());
        }

        userRepository.save(user);
        Instructor updatedInstructor = instructorRepository.save(instructor);

        return mapToProfileDto(updatedInstructor, user);
    }

    @Transactional(readOnly = true)
    public List<InstructorProfileDto> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(instructor -> {
                    User user = userRepository.findById(instructor.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("Associated user not found for instructor id: " + instructor.getId()));
                    return mapToProfileDto(instructor, user);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("instructor not found with id: " + id));

        User user = userRepository.findById(instructor.getUserId()).orElse(null);
        if (user != null) {
            user.getRoles().remove("INSTRUCTOR");
            if (user.getRoles().isEmpty()) {
                user.setActive(false);
            }
            userRepository.save(user);
        }

        instructorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public InstructorProfileDto getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id: " + id));
        User user = userRepository.findById(instructor.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Associated user not found for Instructor id: " + instructor.getId()));
        return mapToProfileDto(instructor, user);
    }

    @Transactional(readOnly = true)
    public List<InstructorListDto> getInstructorList() {
        return instructorRepository.findAll().stream()
                .map(instructor -> {
                    User user = userRepository.findById(instructor.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("User not found for instructor id: " + instructor.getId()));
                    return new InstructorListDto(instructor.getId(), user.getName());
                })
                .collect(Collectors.toList());
    }

    private InstructorProfileDto mapToProfileDto(Instructor instructor, User user) {
        return InstructorProfileDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .phone(user.getPhone())
                .rank(instructor.getRank())
                .build();
    }
}