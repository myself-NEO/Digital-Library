package com.example.library_db.service;

import com.example.library_db.model.Author;
import com.example.library_db.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository autherRepository;

    public List<Author> getAllAuthers() {
        return autherRepository.findAll();
    }

    public Author createOrget(Author myAuthor) {
        Author authorFromDb = autherRepository.findByEmail(myAuthor.getEmail());
        if(authorFromDb != null) {
            return authorFromDb;
        }
        return autherRepository.save(myAuthor);
    }
}
