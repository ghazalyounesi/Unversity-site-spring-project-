// src/test/java/com/example/demo/service/StudentServiceTest.java
package com.example.demo.service;

import com.example.demo.dto.CreateRequest.StudentCreateRequest;
import com.example.demo.dto.ProfileDto.StudentProfileDto;
import com.example.demo.entity.Student;
import com.example.demo.Service.StudentService;

import com.example.demo.entity.User;
import com.example.demo.Repasitory.StudentRepository;
import com.example.demo.Repasitory.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testCreateStudent_Success() {
        StudentCreateRequest request = new StudentCreateRequest();
        request.setUsername("testuser");
        request.setStudentId("S12345");
        request.setDegree(Student.Degree.BS);

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");


        Student savedStudent = new Student();
        savedStudent.setId(10L);
        savedStudent.setUserId(1L);
        savedStudent.setStudentid("S12345");
        savedStudent.setDegree(Student.Degree.BS);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentProfileDto result = studentService.createStudent(request);

        assertNotNull(result);
        assertEquals("S12345", result.getStudentId());
        assertEquals(user.getUsername(), result.getUsername());
    }
}