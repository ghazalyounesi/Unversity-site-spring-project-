package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "terms")
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private boolean open;

    public static Term createOpenTerm(String title) {
        Term term = new Term();
        term.setTitle(title);
        term.setOpen(true);
        return term;
    }

}