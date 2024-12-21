package com.example.netflix.service;

import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.repository.MovieViewCountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieViewCountService {

    private final MovieViewCountRepository movieViewCountRepository;

    public MovieViewCountService(MovieViewCountRepository movieViewCountRepository) {
        this.movieViewCountRepository = movieViewCountRepository;
    }

    public void incrementMovieViewCount(Integer userId, Integer movieId) {
        MovieViewCount movieViewCount = movieViewCountRepository.findByUserAndMovie(userId, movieId)
                .orElseGet(() -> {
                    MovieViewCount newViewCount = new MovieViewCount();
                    newViewCount.setUser(userId);
                    newViewCount.setMovie(movieId);
                    newViewCount.setNumber(0);
                    return newViewCount;
                });
        movieViewCount.incrementViewCount();
        movieViewCountRepository.save(movieViewCount);
    }
}
