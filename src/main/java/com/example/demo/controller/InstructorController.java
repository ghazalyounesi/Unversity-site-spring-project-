// src/main/java/com/example/demo/controller/InstructorController.java
package com.example.demo.controller;

import com.example.demo.dto.CreateRequest.InstructorCreateRequest;
import com.example.demo.dto.ProfileDto.InstructorProfileDto;
import com.example.demo.dto.Update.InstructorUpdateRequest;
import com.example.demo.Service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/instructors")
// In a real app, CUD operations should be secured, e.g., @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    public ResponseEntity<InstructorProfileDto> createInstructor(@RequestBody InstructorCreateRequest request) {
        InstructorProfileDto newInstructor = instructorService.createInstructor(request);
        return ResponseEntity.ok(newInstructor);
    }

    @GetMapping
    public ResponseEntity<List<InstructorProfileDto>> getAllInstructors() {
        List<InstructorProfileDto> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorProfileDto> updateInstructor(@PathVariable Long id, @RequestBody InstructorUpdateRequest request) {
        InstructorProfileDto updatedInstructor = instructorService.updateInstructor(id, request);
        return ResponseEntity.ok(updatedInstructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<InstructorProfileDto> getStudentById(@PathVariable Long id) {
        InstructorProfileDto Instructor = instructorService.getInstructorById(id);
        return ResponseEntity.ok(Instructor);
    }
}