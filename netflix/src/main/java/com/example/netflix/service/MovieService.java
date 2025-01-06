package com.example.netflix.service;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.Profile;
import com.example.netflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(Movie movie) {
        movieRepository.addMovie(movie.getTitle(), movie.getDuration(), movie.isSdAvailable(), movie.isHdAvailable(), movie.isUhdAvailable(), movie.getMinimumAge());
    }

    public Movie getMovieById(Integer id) {
        Optional<Movie> movie = movieRepository.findByMovieId(id);
        return movie.orElse(null);
    }

    public List<Movie> getManyMovies() {
        return movieRepository.findMany();
    }

    public void deleteMovieById(Integer movieId) {
        movieRepository.deleteByMovieId(movieId);
    }

    public void patchMovieById(Integer movieId, Movie patchData) {
        movieRepository.patchByMovieId(movieId, patchData.getTitle(), patchData.getDuration(), patchData.isSdAvailable(), patchData.isHdAvailable(), patchData.isUhdAvailable(), patchData.getMinimumAge());
    }

    public void updateMovieById(Integer movieId, Movie patchData) {
        movieRepository.updateByMovieId(movieId, patchData.getTitle(), patchData.getDuration(), patchData.isSdAvailable(), patchData.isHdAvailable(), patchData.isUhdAvailable(), patchData.getMinimumAge());
    }
}
