package com.example.library_db.repository;

import com.example.library_db.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Override
    Optional<Student> findById(Integer integer);
}