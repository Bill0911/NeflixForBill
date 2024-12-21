package com.example.netflix.service;

import com.example.netflix.entity.GenreForMovie;
import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Movie;
import com.example.netflix.id.GenreForMovieId;
import com.example.netflix.repository.GenreForMovieRepository;
import com.example.netflix.repository.GenreRepository;
import com.example.netflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForMovieService {

    private final GenreForMovieRepository genreForMovieRepository;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public GenreForMovieService(GenreForMovieRepository genreForMovieRepository, MovieRepository movieRepository, GenreRepository genreRepository) {
        this.genreForMovieRepository = genreForMovieRepository;
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    public List<GenreForMovie> getAll() {
        return genreForMovieRepository.findAll();
    }

    public GenreForMovie addGenreForMovie(Integer movieId, Integer genreId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Genre not found"));

        GenreForMovie genreForMovie = new GenreForMovie();
        genreForMovie.setMovie(movie);
        genreForMovie.setGenre(genre);

        return genreForMovieRepository.save(genreForMovie);
    }

    public void deleteGenreForMovie(Integer movieId, Integer genreId) {
        GenreForMovieId id = new GenreForMovieId(movieId, genreId);
        genreForMovieRepository.deleteById(id);
    }

    public GenreForMovie patchGenreForMovie(Integer movieId, Integer oldGenreId, Integer newGenreId) {
        GenreForMovieId id = new GenreForMovieId(movieId, oldGenreId);
        GenreForMovie genreForMovie = genreForMovieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GenreForMovie relationship not found"));

        Genre newGenre = genreRepository.findById(newGenreId).orElseThrow(() -> new RuntimeException("New Genre not found"));

        genreForMovie.setGenre(newGenre);
        return genreForMovieRepository.save(genreForMovie);
    }
}
