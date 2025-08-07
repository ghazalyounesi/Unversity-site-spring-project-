package com.example.demo.controller;

import com.example.demo.Service.InstructorService;
import com.example.demo.model.dto.CreateRequest.InstructorCreateRequest;
import com.example.demo.model.dto.ListDto.InstructorListDto;
import com.example.demo.model.dto.ProfileDto.InstructorProfileDto;
import com.example.demo.model.dto.Update.InstructorUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<InstructorProfileDto> createInstructor(@RequestBody InstructorCreateRequest request) {
        InstructorProfileDto newInstructor = instructorService.createInstructor(request);
        return ResponseEntity.ok(newInstructor);
    }

    @GetMapping("/read")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<InstructorProfileDto>> getAllInstructors() {
        List<InstructorProfileDto> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<InstructorListDto>> listInstructor() {
        List<InstructorListDto> instructorList = instructorService.getInstructorList();
        return ResponseEntity.ok(instructorList);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<InstructorProfileDto> updateInstructor(@PathVariable Long id, @RequestBody InstructorUpdateRequest request) {
        InstructorProfileDto updatedInstructor = instructorService.updateInstructor(id, request);
        return ResponseEntity.ok(updatedInstructor);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
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