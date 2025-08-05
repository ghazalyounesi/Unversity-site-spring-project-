package com.example.demo.service;

import com.example.demo.Repasitory.*;
import com.example.demo.dto.ListDto.CourseGradeDto;
import com.example.demo.dto.ListDto.TermGradesDto;
import com.example.demo.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.demo.Service.StudentGradeService;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentGradeServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private CourseSectionRepository courseSectionRepository;
    @Mock private CourseSectionRegistrationRepository registrationRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private InstructorRepository instructorRepository;

    @InjectMocks
    private StudentGradeService studentGradeService;

    @BeforeEach
    void setUp() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("student1");

        // ست کردن SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(mockUser.getUsername(), null));
        SecurityContextHolder.setContext(context);
    }

    @Test
    void testGetTermGrades_returnsCorrectGpaAndCourseList() {
        // داده‌ها
        Long termId = 100L;
        Long studentId = 10L;
        Long userId = 1L;

        // User
        User user = new User();
        user.setId(userId);
        user.setUsername("student1");

        // Student
        Student student = new Student();
        student.setId(studentId);
        student.setUserId(userId);

        // Course 1
        Course course1 = new Course();
        course1.setId(1L);
        course1.setUnits(3);
        course1.setTitle("Math");

        CourseSection section1 = new CourseSection();
        section1.setId(11L);
        section1.setCourseId(1L);
        section1.setTermId(termId);
        section1.setInstructorId(201L);

        CourseSectionRegistration reg1 = new CourseSectionRegistration();
        reg1.setCourseSectionId(11L);
        reg1.setScore(18.0);

        Instructor instructor1 = new Instructor();
        instructor1.setId(201L);
        instructor1.setUserId(301L);

        User instructorUser1 = new User();
        instructorUser1.setId(301L);
        instructorUser1.setName("Dr. Smith");

        // Course 2
        Course course2 = new Course();
        course2.setId(2L);
        course2.setUnits(2);
        course2.setTitle("Physics");

        CourseSection section2 = new CourseSection();
        section2.setId(12L);
        section2.setCourseId(2L);
        section2.setTermId(termId);
        section2.setInstructorId(202L);

        CourseSectionRegistration reg2 = new CourseSectionRegistration();
        reg2.setCourseSectionId(12L);
        reg2.setScore(16.0);

        Instructor instructor2 = new Instructor();
        instructor2.setId(202L);
        instructor2.setUserId(302L);

        User instructorUser2 = new User();
        instructorUser2.setId(302L);
        instructorUser2.setName("Dr. Johnson");

        // Mock رفتار repositoryها
        when(userRepository.findByUsername("student1")).thenReturn(Optional.of(user));
        when(studentRepository.findByUserId(userId)).thenReturn(Optional.of(student));
        when(courseSectionRepository.findAllByTermId(termId)).thenReturn(List.of(section1, section2));
        when(registrationRepository.findByStudentIdAndCourseSectionIdIn(eq(studentId), anyList()))
                .thenReturn(List.of(reg1, reg2));
        when(courseSectionRepository.findById(11L)).thenReturn(Optional.of(section1));
        when(courseSectionRepository.findById(12L)).thenReturn(Optional.of(section2));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course2));
        when(instructorRepository.findById(201L)).thenReturn(Optional.of(instructor1));
        when(instructorRepository.findById(202L)).thenReturn(Optional.of(instructor2));
        when(userRepository.findById(301L)).thenReturn(Optional.of(instructorUser1));
        when(userRepository.findById(302L)).thenReturn(Optional.of(instructorUser2));

        // اجرا
        TermGradesDto result = studentGradeService.getTermGrades(termId);

        // بررسی
        assertNotNull(result);
        assertEquals(2, result.getCourses().size());

        // محاسبه GPA = (18*3 + 16*2) / (3+2) = (54 + 32) / 5 = 17.2
        assertEquals(17.2, result.getTermGpa(), 0.01);

        CourseGradeDto course1Grade = result.getCourses().get(0);
        assertEquals("Math", course1Grade.getCourseTitle());
        assertEquals("Dr. Smith", course1Grade.getInstructorName());

        CourseGradeDto course2Grade = result.getCourses().get(1);
        assertEquals("Physics", course2Grade.getCourseTitle());
        assertEquals("Dr. Johnson", course2Grade.getInstructorName());
    }
}
