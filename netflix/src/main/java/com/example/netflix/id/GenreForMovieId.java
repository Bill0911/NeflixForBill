package com.example.netflix.id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenreForMovieId)) return false;
        GenreForMovieId that = (GenreForMovieId) o;
        return Objects.equals(getGenre(), that.getGenre()) &&
                Objects.equals(getMovie(), that.getMovie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGenre(), getMovie());
    }
}
