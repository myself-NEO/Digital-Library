package com.example.library_db.service;

import com.example.library_db.controller.AutherController;
import com.example.library_db.model.Auther;
import com.example.library_db.repository.AutherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AutherRepository autherRepository;

    public List<Auther> getAllAuthers() {
        return autherRepository.findAll();
    }
}
