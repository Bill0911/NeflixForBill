package com.example.netflix.service;

import com.example.netflix.entity.MethodResponse;
import com.example.netflix.entity.Movie;
import com.example.netflix.entity.MovieViewCount;
import com.example.netflix.entity.User;
import com.example.netflix.repository.MovieViewCountRepository;
import com.example.netflix.repository.MovieRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie)
    {
        return  movieRepository.save(movie);
    }
}
