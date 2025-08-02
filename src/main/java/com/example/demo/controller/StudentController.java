// src/main/java/com/example/demo/controller/StudentController.java
package com.example.demo.controller;

import com.example.demo.dto.CreateRequest.StudentCreateRequest;
import com.example.demo.dto.ProfileDto.StudentProfileDto;
import com.example.demo.dto.Update.StudentUpdateRequest;
import com.example.demo.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
// In a real app, CUD operations should be secured, e.g., @PreAuthorize("hasRole('ADMIN')")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentProfileDto> createStudent(@RequestBody StudentCreateRequest request) {
        StudentProfileDto newStudent = studentService.createStudent(request);
        return ResponseEntity.ok(newStudent);
    }

    @GetMapping
    public ResponseEntity<List<StudentProfileDto>> getAllStudents() {
        List<StudentProfileDto> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentProfileDto> getStudentById(@PathVariable Long id) {
        StudentProfileDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentProfileDto> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateRequest request) {
        StudentProfileDto updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}