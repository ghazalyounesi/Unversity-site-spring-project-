package com.example.demo.controller;

import com.example.demo.Service.StaffService;
import com.example.demo.dto.CreateRequest.StaffCreateRequest;
import com.example.demo.dto.ListDto.staffListDto;
import com.example.demo.dto.ProfileDto.StaffProfileDto;
import com.example.demo.dto.Update.StaffUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
@PreAuthorize("hasRole('ADMIN')")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    public ResponseEntity<StaffProfileDto> createStaff(@RequestBody StaffCreateRequest request) {
        StaffProfileDto newStaff = staffService.createStaff(request);
        return ResponseEntity.ok(newStaff);
    }

    @GetMapping("/read")
    public ResponseEntity<List<StaffProfileDto>> getAllStaff() {
        List<StaffProfileDto> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/list")
    public ResponseEntity<List<staffListDto>> listStudents() {
        List<staffListDto> staffList = staffService.getStaffList();
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffProfileDto> getStaffById(@PathVariable Long id) {
        StaffProfileDto student = staffService.getStaffById(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffProfileDto> updateStaff(@PathVariable Long id, @RequestBody StaffUpdateRequest request) {
        StaffProfileDto updatedStaff = staffService.updateStaff(id, request);
        return ResponseEntity.ok(updatedStaff);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}