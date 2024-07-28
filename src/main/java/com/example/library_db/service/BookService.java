package com.example.library_db.service;

import com.example.library_db.dto.CreateBookRequest;
import com.example.library_db.dto.SearchBookRequest;
import com.example.library_db.model.Author;
import com.example.library_db.model.Book;
import com.example.library_db.model.Student;
import com.example.library_db.model.enums.Genre;
import com.example.library_db.repository.AuthorRepository;
import com.example.library_db.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository autherRepository;

    @Autowired
    AuthorService authorService;

    public Book create(CreateBookRequest createBookRequest) {
        Book book = createBookRequest.to();
        Author author = authorService.createOrget(book.getMy_author());
        book.setMy_author(author);
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public Book deleteBookById(int bookId) {
        Book book = this.getBookById(bookId);
        if(book != null) bookRepository.deleteById(bookId);
        return book;
    }

    public List<Book> searchBooks(SearchBookRequest searchBookRequest) throws Exception {
        Boolean validRequest = searchBookRequest.validate();
        if(!validRequest) throw new Exception("Invalid Request");
        switch (searchBookRequest.getSearchKey()){
            case "name":
                return bookRepository.findByName(searchBookRequest.getSearchValue());
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchBookRequest.getSearchValue()));
            case "id": {
                Book book = bookRepository.findById(Integer.parseInt(searchBookRequest.getSearchValue())).orElse(null);
                return Arrays.asList(book);
            }

            default:
                throw new Exception("invalid search key");
        }
    }

    public void assignBookToStudent(Book book, Student student){
        bookRepository.assignBookToStudent(book.getId(), student);
    }

    public void unassignBookFromStudent(Book book){
        bookRepository.unassignBook(book.getId());
    }
}
