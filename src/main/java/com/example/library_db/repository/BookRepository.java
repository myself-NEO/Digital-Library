package com.example.library_db.repository;

import com.example.library_db.model.Book;
import com.example.library_db.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByName(String searchValue);

    List<Book> findByGenre(Genre genre);
}