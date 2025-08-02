// src/main/java/com/example/demo/Repasitory/InstructorRepository.java
package com.example.demo.Repasitory;

import com.example.demo.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}