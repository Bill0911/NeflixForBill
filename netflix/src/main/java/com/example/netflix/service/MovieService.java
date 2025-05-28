package com.example.netflix.service;

import com.example.netflix.dto.MovieDTO;
import com.example.netflix.entity.Movie;
import com.example.netflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(MovieDTO dto) {
        Movie movie = toEntity(dto);
        movieRepository.save(movie);
    }

    public Optional<MovieDTO> getMovieById(Integer id) {
        return movieRepository.findById(id).map(this::toDTO);
    }

    public List<MovieDTO> getManyMovies() {
        return movieRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteMovieById(Integer movieId) {
        movieRepository.deleteById(movieId);
    }

    public void patchMovieById(Integer movieId, MovieDTO patchData) {
        movieRepository.findById(movieId).ifPresent(movie -> {
            if (patchData.getTitle() != null) movie.setTitle(patchData.getTitle());
            // Add more patchable fields as needed
            movieRepository.save(movie);
        });
    }

    public void updateMovieById(Integer movieId, MovieDTO patchData) {
        movieRepository.findById(movieId).ifPresent(movie -> {
            movie.setTitle(patchData.getTitle());
            // Add more updatable fields as needed
            movieRepository.save(movie);
        });
    }

    public List<MovieDTO> getMoviesWithoutGenre() {
        return movieRepository.findMoviesWithoutGenre().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MovieDTO toDTO(Movie movie) {
        return new MovieDTO(movie.getMovieId(), movie.getTitle());
    }

    private Movie toEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setMovieId(dto.getMovieId());
        movie.setTitle(dto.getTitle());
        // Set more fields as needed
        return movie;
    }
}
