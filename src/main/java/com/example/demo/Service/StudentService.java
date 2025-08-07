package com.example.demo.Service;

import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.dto.CreateRequest.StudentCreateRequest;
import com.example.demo.model.dto.ListDto.StudentListDto;
import com.example.demo.model.dto.ProfileDto.StudentProfileDto;
import com.example.demo.model.dto.Update.StudentUpdateRequest;
import com.example.demo.model.entity.Student;
import com.example.demo.model.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public StudentProfileDto createStudent(StudentCreateRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + request.getUsername()));

        Student student = new Student();
        student.setUserId(user.getId());
        student.setStudentid(request.getStudentId());
        student.setDegree(request.getDegree());
        student.setStartDate(request.getStartDate());
        Student savedStudent = studentRepository.save(student);

        user.getRoles().add("STUDENT");
        user.setActive(true);
        userRepository.save(user);

        return mapToProfileDto(savedStudent, user);
    }

    @Transactional
    public StudentProfileDto updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        User user = userRepository.findById(student.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Associated user not found for student id: " + student.getId()));

        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            user.setPhone(request.getPhone());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getDegree() != null) {
            student.setDegree(request.getDegree());
        }

        userRepository.save(user);
        Student updatedStudent = studentRepository.save(student);

        return mapToProfileDto(updatedStudent, user);
    }

    @Transactional(readOnly = true)
    public List<StudentProfileDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> {
                    User user = userRepository.findById(student.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("Associated user not found for student id: " + student.getId()));
                    return mapToProfileDto(student, user);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentListDto> getStudentList() {
        return studentRepository.findAll().stream()
                .map(student -> {
                    User user = userRepository.findById(student.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException("User not found for student id: " + student.getId()));
                    return new StudentListDto(student.getId(), user.getName(), student.getStudentid());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        User user = userRepository.findById(student.getUserId()).orElse(null);
        if (user != null) {
            user.getRoles().remove("STUDENT");
            if (user.getRoles().isEmpty()) {
                user.setActive(false);
            }
            userRepository.save(user);
        }

        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public StudentProfileDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        User user = userRepository.findById(student.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Associated user not found for student id: " + student.getId()));
        return mapToProfileDto(student, user);
    }

    private StudentProfileDto mapToProfileDto(Student student, User user) {
        return StudentProfileDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .phone(user.getPhone())
                .studentId(student.getStudentid())
                .degree(student.getDegree())
                .startDate(student.getStartDate())
                .build();
    }
}