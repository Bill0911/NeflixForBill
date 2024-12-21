package com.example.netflix.service;

import com.example.netflix.entity.Movie;
import com.example.netflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Integer movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Integer movieId, Movie updatedMovie) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));
        movie.setTitle(updatedMovie.getTitle());
        movie.setDuration(updatedMovie.getDuration());
        movie.setMinimumAge(updatedMovie.getMinimumAge());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Integer movieId) {
        movieRepository.deleteById(movieId);
    }

    public Movie patchMovie(Integer movieId, Movie patchData) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));

        if (patchData.getTitle() != null) {
            movie.setTitle(patchData.getTitle());
        }
        if (patchData.getDuration() != null) {
            movie.setDuration(patchData.getDuration());
        }
        if (patchData.getMinimumAge() != null) {
            movie.setMinimumAge(patchData.getMinimumAge());
        }

        return movieRepository.save(movie);
    }
}
