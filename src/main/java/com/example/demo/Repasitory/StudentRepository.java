// src/main/java/com/example/demo/Repasitory/StudentRepository.java
package com.example.demo.Repasitory;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}