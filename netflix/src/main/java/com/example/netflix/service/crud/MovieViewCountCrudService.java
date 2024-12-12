package com.example.netflix.service.crud;

import com.example.netflix.repository.crud.MovieViewCountCrudRepository;

public class MovieViewCountCrudService {
    private final MovieViewCountCrudRepository movieViewCountCrudRepository;

    public MovieViewCountCrudService(MovieViewCountCrudRepository movieViewCountCrudRepository) {
        this.movieViewCountCrudRepository = movieViewCountCrudRepository;
    }

    public void incrementViewCount(Integer movieId, Integer accountId) {
        movieViewCountCrudRepository.addMovieViewCount(movieId, accountId);
    }
}
