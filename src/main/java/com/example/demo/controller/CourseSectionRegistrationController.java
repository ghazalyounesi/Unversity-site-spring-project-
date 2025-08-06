package com.example.demo.controller;

import com.example.demo.Service.CourseSectionRegistrationService;
import com.example.demo.dto.CreateRequest.CourseSectionRegistrationRequestDto;
import com.example.demo.entity.CourseSectionRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registrations")
@RequiredArgsConstructor
public class CourseSectionRegistrationController {

    private final CourseSectionRegistrationService registrationService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<CourseSectionRegistration> registerForCourse(@RequestBody CourseSectionRegistrationRequestDto request) {
        CourseSectionRegistration registration = registrationService.createRegistration(request);
        return ResponseEntity.ok(registration);
    }
}