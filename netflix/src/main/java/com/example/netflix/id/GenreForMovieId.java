package com.example.netflix.id;

import java.io.Serializable;
import java.util.Objects;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Movie;


public class GenreForMovieId implements Serializable {
    private Genre genre;
    private Movie movie;

    public GenreForMovieId(Genre genre, Movie movie) {
        this.genre = genre;
        this.movie = movie;
    }

    public GenreForMovieId() {

    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}