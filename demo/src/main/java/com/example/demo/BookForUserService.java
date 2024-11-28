package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookForUserService {

    @Autowired
    private BookForUserRepository bookForUserRepository;

    public void save(BookForUser bookForUser) {
        bookForUserRepository.save(bookForUser);
    }
}

