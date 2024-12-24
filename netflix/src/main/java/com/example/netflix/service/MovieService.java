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

    public void patchMovie(Integer movieId, Movie patchData) {
        System.out.println(patchData.getTitle() + ", " + patchData.getDuration() + ", SD:" + patchData.isSdAvailable() + ", HD:" + patchData.isHdAvailable() + ", UHD:" + patchData.isUhdAvailable() + ", " + patchData.getMinimumAge());
        movieRepository.patchById(movieId, patchData.getTitle(), patchData.getDuration(), patchData.isSdAvailable(), patchData.isHdAvailable(), patchData.isUhdAvailable(), patchData.getMinimumAge());
    }
}
