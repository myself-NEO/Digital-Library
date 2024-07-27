package com.example.library_db.repository;

import com.example.library_db.model.Auther;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutherRepository extends JpaRepository<Auther, Integer> {
//    @Query("select a from Auther a where a.email = ?1")
    Auther findByEmail(String email);


}