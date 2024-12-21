package com.example.netflix.id;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Movie;

import java.io.Serializable;
import java.util.Objects;

public class GenreForMovieId implements Serializable {

    private Integer genre;
    private Integer movie;

    public GenreForMovieId() {}

    public GenreForMovieId(Integer genre, Integer movie) {
        this.genre = genre;
        this.movie = movie;
    }

    // Getters and Setters
    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public Integer getMovie() {
        return movie;
    }

    public void setMovie(Integer movie) {
        this.movie = movie;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreForMovieId that = (GenreForMovieId) o;
        return Objects.equals(genre, that.genre) && Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre, movie);
    }
}
