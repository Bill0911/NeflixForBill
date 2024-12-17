package com.example.netflix.service;

import com.example.netflix.entity.UserGenreCount;
import com.example.netflix.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalizedOfferService
{
    private UserRepository userRepository;
    private GenreForUserRepository genreForUserRepository;

    private final UserGenreCountRepository userGenreCountRepository;
    private GenreForSeriesRepository genreForSeriesRepository;
    private GenreForMovieRepository genreForMovieRepository;

    public PersonalizedOfferService(UserGenreCountRepository userGenreCountRepository) {
        this.userGenreCountRepository = userGenreCountRepository;
    }

    public List<UserGenreCount> getUserGenreCounts(Integer userId) {
        return userGenreCountRepository.findByUserId(userId);
    }
}
