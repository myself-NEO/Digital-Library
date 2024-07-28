package com.example.library_db.controller;

import com.example.library_db.model.Author;
import com.example.library_db.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/author/all")
    public List<Author> getAuthors(){
        return authorService.getAllAuthers();
    }
}
