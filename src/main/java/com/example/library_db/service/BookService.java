package com.example.library_db.service;

import com.example.library_db.dto.CreateBookRequest;
import com.example.library_db.dto.SearchBookRequest;
import com.example.library_db.model.Auther;
import com.example.library_db.model.Book;
import com.example.library_db.model.enums.Genre;
import com.example.library_db.repository.AutherRepository;
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
    AutherRepository autherRepository;

    @Autowired
    AutherService autherService;

    public Book create(CreateBookRequest createBookRequest) {
        Book book = createBookRequest.to();
        Auther auther = autherService.createOrget(book.getMy_auther());
        book.setMy_auther(auther);
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
}
