package com.example.library_db.controller;

import com.example.library_db.dto.CreateBookRequest;
import com.example.library_db.dto.SearchBookRequest;
import com.example.library_db.model.Book;
import com.example.library_db.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/createBook")
    public Book createBook(@RequestBody @Valid CreateBookRequest createBookRequest){
        return bookService.create(createBookRequest);
    }

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") int bookId) {
        return bookService.getBookById(bookId);
    }

    @DeleteMapping("/{id}")
    public Book deleteBookById(@PathVariable("id") int bookId) {
        return bookService.deleteBookById(bookId);
    }

    @GetMapping("/search")
    public List<Book> serachBooks(@RequestBody @Valid SearchBookRequest searchBookRequest) throws Exception {
        return bookService.searchBooks(searchBookRequest);
    }
}
