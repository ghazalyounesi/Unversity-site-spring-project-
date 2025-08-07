package com.example.demo.Service;

import com.example.demo.Repository.TermRepository;
import com.example.demo.model.dto.ProfileDto.TermDto;
import com.example.demo.model.entity.Term;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;

    public Term createTerm(TermDto dto) {
        Term term = Term.builder()
                .title(dto.getTitle())
                .open(dto.isOpen())
                .build();

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
