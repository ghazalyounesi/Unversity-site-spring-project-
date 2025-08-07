package com.example.demo.controller;

import com.example.demo.Repository.*;
import com.example.demo.model.dto.CreateRequest.CourseSectionCreateDto;
import com.example.demo.model.dto.ListDto.GradeEntryDto;
import com.example.demo.model.dto.Login.LoginRequest;
import com.example.demo.model.entity.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FullAcademicWorkflowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String instructorToken;
    private String studentToken;
    private Term term;
    private Course course;
    private Student student;

    @BeforeEach
    void setUp() throws Exception {
        User instructorUser = createUser("flow_instructor", "Flow Instructor", "INSTRUCTOR");
        User studentUser = createUser("flow_student", "Flow Student", "STUDENT");

        createInstructor(instructorUser);
        student = createStudent(studentUser, "S-Flow");
        term = createTerm("Workflow Term");
        course = createCourse("Workflow Course", 3);

        instructorToken = loginAndGetToken("flow_instructor", "password123");
        studentToken = loginAndGetToken("flow_student", "password123");
    }

    @Test
    void testFullEnrollGradeAndViewWorkflow() throws Exception {
        CourseSectionCreateDto createSectionDto = CourseSectionCreateDto.builder()
                .termId(term.getId())
                .courseId(course.getId())
                .build();


        MvcResult createSectionResult = mockMvc.perform(post("/api/v1/course-sections")
                        .header("Authorization", "Bearer " + instructorToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSectionDto)))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode sectionJson = objectMapper.readTree(createSectionResult.getResponse().getContentAsString());
        long courseSectionId = sectionJson.get("id").asLong();

        String enrollmentRequestBody = String.format("{\"courseSectionId\": %d}", courseSectionId);
        mockMvc.perform(post("/api/v1/registrations")
                        .header("Authorization", "Bearer " + studentToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enrollmentRequestBody))
                .andExpect(status().isOk());

        GradeEntryDto gradeDto = GradeEntryDto.of(student.getId(), 19.5);
        gradeDto.setStudentId(student.getId());
        gradeDto.setScore(19.5);

        mockMvc.perform(post("/api/v1/course-sections/{id}/grades", courseSectionId)
                        .header("Authorization", "Bearer " + instructorToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gradeDto)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/student/grades/term/{id}", term.getId())
                        .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.termGpa", is(19.5)))
                .andExpect(jsonPath("$.courses[0].courseTitle", is("Workflow Course")))
                .andExpect(jsonPath("$.courses[0].score", is(19.5)));
    }


    private String loginAndGetToken(String username, String password) throws Exception {
        LoginRequest loginRequest = LoginRequest.of(username, password);

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(responseJson);
        return node.get("token").asText();
    }

    private User createUser(String username, String name, String role) {
        User user = User.builder()
                .username(username)
                .name(name)
                .password(passwordEncoder.encode("password123"))
                .phone(username + "@example.com")
                .nationalld(String.valueOf(System.nanoTime()))
                .active(true)
                .roles(Set.of(role))
                .build();

        return userRepository.save(user);
    }


    private Instructor createInstructor(User user) {
        Instructor instructor = Instructor.createAssistantInstructor(user);
        return instructorRepository.save(instructor);
    }


    private Student createStudent(User user, String studentCode) {
        Student student = Student.builder()
                .userId(user.getId())
                .studentid(studentCode)
                .degree(Student.Degree.BS)
                .build();
        return studentRepository.save(student);
    }


    private Term createTerm(String title) {
        Term t = Term.createOpenTerm("Fall 2025");
        return termRepository.save(t);
    }

    private Course createCourse(String title, int units) {
        Course c = Course.createCourse(title, units);
        return courseRepository.save(c);
    }
}
