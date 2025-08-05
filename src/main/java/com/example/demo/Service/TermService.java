package com.example.demo.Service;

import com.example.demo.Repasitory.TermRepository;
import com.example.demo.dto.ProfileDto.TermDto;
import com.example.demo.entity.Term;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;

    public Term createTerm(TermDto dto) {
        Term term = new Term();
        term.setTitle(dto.getTitle());
        term.setOpen(dto.isOpen());
        return termRepository.save(term);
    }

    public List<Term> getAllTerms() {
        return termRepository.findAll();
    }

    public Term getTermById(Long id) {
        return termRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Term not found with id: " + id));
    }

    public Term updateTerm(Long id, TermDto dto) {
        Term existingTerm = getTermById(id);
        existingTerm.setTitle(dto.getTitle());
        existingTerm.setOpen(dto.isOpen());
        return termRepository.save(existingTerm);
    }

    public void deleteTerm(Long id) {
        termRepository.deleteById(id);
    }
}
