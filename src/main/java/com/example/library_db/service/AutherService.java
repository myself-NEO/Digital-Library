package com.example.library_db.service;

import com.example.library_db.model.Auther;
import com.example.library_db.repository.AutherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutherService {

    @Autowired
    AutherRepository autherRepository;

    public Auther createOrget(Auther myAuther) {
        Auther autherFromDb = autherRepository.findByEmail(myAuther.getEmail());
        if(autherFromDb != null) {
            return autherFromDb;
        }
        return autherRepository.save(myAuther);
    }
}
