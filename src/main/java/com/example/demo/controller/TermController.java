package com.example.demo.controller;

import com.example.demo.Service.TermService;
import com.example.demo.model.dto.ProfileDto.TermDto;
import com.example.demo.model.entity.Term;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/terms")
@RequiredArgsConstructor
public class TermController {

    private final TermService termService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Term> createTerm(@RequestBody TermDto dto) {
        return ResponseEntity.ok(termService.createTerm(dto));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Term>> getAllTerms() {
        return ResponseEntity.ok(termService.getAllTerms());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Term> getTermById(@PathVariable Long id) {
        return ResponseEntity.ok(termService.getTermById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Term> updateTerm(@PathVariable Long id, @RequestBody TermDto dto) {
        return ResponseEntity.ok(termService.updateTerm(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Void> deleteTerm(@PathVariable Long id) {
        termService.deleteTerm(id);
        return ResponseEntity.noContent().build();
    }
}