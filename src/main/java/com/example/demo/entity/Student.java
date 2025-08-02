// src/main/java/com/example/demo/entity/Student.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Degree {
        BS, MS, PHD
    }

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
    public Student() {}
    public Student(Long userId,String studentid, Degree degree, Date startDate) {
        this.studentid = studentid;
        this.degree = degree;
        this.startDate = startDate;
        this.userId = userId;
    }
}