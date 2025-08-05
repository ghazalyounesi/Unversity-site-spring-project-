// src/main/java/com/example/demo/TestDataInitializer.java
package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.Repasitory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TestDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final TermRepository termRepository;
    private final CourseRepository courseRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final CourseSectionRegistrationRepository registrationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (termRepository.count() == 0) {
            System.out.println(">>>> Initializing rich test data for grading...");

            User instructorUser = createUser("prof_reza", "رضا رضایی", "INSTRUCTOR");
            User studentUser = createUser("sara_student", "سارا محمدی", "STUDENT");

            Instructor instructor = createInstructor(instructorUser);
            Student student = createStudent(studentUser, "9912345");

            Term term1 = createTerm("ترم پاییز 1403");
            Term term2 = createTerm("ترم بهار 1404");

            Course course1 = createCourse("ساختمان داده‌ها", 3);
            Course course2 = createCourse("ریاضی 1", 3);
            Course course3 = createCourse("فیزیک 2", 4);

            CourseSection cs1 = createCourseSection(term1, course1, instructor);
            CourseSection cs2 = createCourseSection(term1, course2, instructor);
            CourseSection cs3 = createCourseSection(term2, course3, instructor);

            enrollStudent(student, cs1, 18.5);
            enrollStudent(student, cs2, 16.0);
            enrollStudent(student, cs3, null);

            System.out.println(">>>> Test data for grading is complete!");
            System.out.println(">>>> Login with user: 'sara_student', pass: 'password123'");
            System.out.println(">>>> Request grades for Term with ID: " + term1.getId());
        }
    }

    private void enrollStudent(Student s, CourseSection cs, Double score) {
        CourseSectionRegistration r = new CourseSectionRegistration();
        r.setStudentId(s.getId());
        r.setCourseSectionId(cs.getId());
        r.setScore(score);
        registrationRepository.save(r);
    }


    private User createUser(String username, String name, String role) {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setPassword(passwordEncoder.encode("password123"));
        user.setPhone(username + "@example.com");
        user.setNationalld(String.valueOf(System.nanoTime()));
        user.setActive(true);
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    private Instructor createInstructor(User user) {
        Instructor i = new Instructor();
        i.setUserId(user.getId());
        i.setRank(Instructor.Rank.ASSISTANT);
        return instructorRepository.save(i);
    }

    private Student createStudent(User user, String studentCode) {
        Student s = new Student();
        s.setUserId(user.getId());
        s.setStudentid(studentCode);
        s.setDegree(Student.Degree.BS);
        return studentRepository.save(s);
    }

    private Term createTerm(String title) {
        Term t = new Term();
        t.setTitle(title);
        t.setOpen(true);
        return termRepository.save(t);
    }

    private Course createCourse(String title, int units) {
        Course c = new Course();
        c.setTitle(title);
        c.setUnits(units);
        return courseRepository.save(c);
    }

    private CourseSection createCourseSection(Term t, Course c, Instructor i) {
        CourseSection cs = new CourseSection();
        cs.setTermId(t.getId());
        cs.setCourseId(c.getId());
        cs.setInstructorId(i.getId());
        return courseSectionRepository.save(cs);
    }

    private void enrollStudent(Student s, CourseSection cs) {
        CourseSectionRegistration r = new CourseSectionRegistration();
        r.setStudentId(s.getId());
        r.setCourseSectionId(cs.getId());
        registrationRepository.save(r);
    }}