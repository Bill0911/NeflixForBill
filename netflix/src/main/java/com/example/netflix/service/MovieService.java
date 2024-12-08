package com.example.netflix.service;

import com.example.netflix.entity.*;
import com.example.netflix.repository.MovieViewCountRepository;
import com.example.netflix.repository.MovieRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies()
    {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Integer id)
    {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));
    }

    public Movie createMovie(Movie movie)
    {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Integer id, Movie movieDetails)
    {
        Movie movie = getMovieById(id);
        movie.setTitle(movieDetails.getTitle());
        movie.setDuration(movieDetails.getDuration());
        movie.setMinimumAge(movieDetails.getMinimumAge());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Integer id)
    {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }
}
