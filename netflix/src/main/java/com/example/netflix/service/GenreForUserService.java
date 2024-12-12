package com.example.netflix.service;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.entity.GenreForUser;
import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.id.GenreForUserId;
import com.example.netflix.repository.GenreForSeriesRepository;
import com.example.netflix.repository.GenreForUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForUserService
{

    @Autowired
    private GenreForUserRepository genreForUserRepository;

    public GenreForUser save(GenreForUser genreForUser)
    {
        return genreForUserRepository.save(genreForUser);
    }

    public GenreForUser findById(GenreForUserId id)
    {
        return genreForUserRepository.findById(id).orElse(null);
    }

    public List<GenreForUser> findAll()
    {
        return genreForUserRepository.findAll();
    }

    public GenreForUser update(GenreForUserId targetGFU, GenreForUser newGFU) {
        if (genreForUserRepository.existsById(targetGFU)) {
            newGFU.setGenre(targetGFU.getGenre());
            newGFU.setUser(targetGFU.getUser());
            return genreForUserRepository.save(newGFU);
        }
        return null;
    }

    public void delete(GenreForUserId id)
    {
        genreForUserRepository.deleteById(id);
    }
}