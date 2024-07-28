package com.example.library_db.repository;

import com.example.library_db.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
//    @Query("select a from Auther a where a.email = ?1")
    Author findByEmail(String email);


}