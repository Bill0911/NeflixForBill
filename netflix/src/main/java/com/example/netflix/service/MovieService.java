package com.example.netflix.service;

import com.example.netflix.entity.Movie;
import com.example.netflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService
{

    private final MovieRepository movieRepository;

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
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));
    }

    public Movie createMovie(Movie movie)
    {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Integer id, Movie updatedMovie)
    {
        return movieRepository.findById(id)
                .map(existingMovie -> {
                    existingMovie.setTitle(updatedMovie.getTitle());
                    existingMovie.setDuration(updatedMovie.getDuration());
                    existingMovie.setMinimumAge(updatedMovie.getMinimumAge());
                    return movieRepository.save(existingMovie);
                })
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));
    }

    public void deleteMovie(Integer id)
    {
        if (!movieRepository.existsById(id))
        {
            throw new RuntimeException("Movie not found with ID: " + id);
        }
        movieRepository.deleteById(id);
    }
}
