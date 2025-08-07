package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Setter
@Getter

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "student_id", unique = true)
    private String studentid;
    @Column(nullable = false)
    private Long userId; // Foreign key to User entity
    @Enumerated(EnumType.STRING)
    @Column(name = "degree")
    private Degree degree;
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;


    public enum Degree {
        BS, MS, PHD
    }
}