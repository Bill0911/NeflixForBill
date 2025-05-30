package com.example.netflix.service;

import com.example.netflix.entity.GenreForUser;
import com.example.netflix.entity.GenreForUser;
import com.example.netflix.id.GenreForUserId;
import com.example.netflix.repository.GenreForUserRepository;
import com.example.netflix.repository.GenreForUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForUserService {
    private final GenreForUserRepository genreForUserRepository;


    public GenreForUserService(GenreForUserRepository genreForUserRepository)
    {
        this.genreForUserRepository = genreForUserRepository;
    }

    public void addGenreForUser(Integer id1, Integer id2) {
        genreForUserRepository.add(id1, id2);
    }

    public GenreForUser getGenreForUser(Integer id1, Integer id2) {
        return genreForUserRepository.find(id1, id2).orElse(null);
    }

    public List<GenreForUser> getManyGenreForUsers() {
        return genreForUserRepository.findMany();
    }

    public void deleteGenreForUser(Integer id1, Integer id2) {
        genreForUserRepository.delete(id1, id2);
    }

}
