package com.example.library_db.repository;

import com.example.library_db.model.Book;
import com.example.library_db.model.Student;
import com.example.library_db.model.enums.Genre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByName(String searchValue);

    List<Book> findByGenre(Genre genre);

    @Modifying // for DML support
    @Transactional // for updating any data
    @Query("update Book b set b.my_student = ?2 where b.id = ?1 and b.my_student is null")
    void assignBookToStudent(int id, Student student);

    @Modifying // for DML support
    @Transactional // for updating any data
    @Query("update Book b set b.my_student = null where b.id = ?1 ")
    void unassignBook(int id);
}