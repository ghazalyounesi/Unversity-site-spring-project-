// src/main/java/com/example/demo/controller/StudentController.java
package com.example.demo.controller;

import com.example.demo.Service.StudentService;
import com.example.demo.dto.CreateRequest.StudentCreateRequest;
import com.example.demo.dto.ListDto.StudentListDto;
import com.example.demo.dto.ProfileDto.StudentProfileDto;
import com.example.demo.dto.Update.StudentUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<StudentProfileDto> createStudent(@RequestBody StudentCreateRequest request) {
        StudentProfileDto newStudent = studentService.createStudent(request);
        return ResponseEntity.ok(newStudent);
    }

    @GetMapping(("/read"))
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<StudentProfileDto>> getAllStudents() {
        List<StudentProfileDto> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }


    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<StudentListDto>> listStudents() {
        List<StudentListDto> students = studentService.getStudentList();
        return ResponseEntity.ok(students);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentProfileDto> getStudentById(@PathVariable Long id) {
        StudentProfileDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<StudentProfileDto> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateRequest request) {
        StudentProfileDto updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}