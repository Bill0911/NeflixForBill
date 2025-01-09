package com.example.netflix.entity;

import com.example.netflix.id.GenreForMovieId;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name = "genreformovie")
@IdClass(GenreForMovieId.class)
public class GenreForMovie {

    @Id
    @Column(name = "movie_id")
    private Integer movie;

    @Id
    @Column(name = "genre_id")
    private Integer genre;

    public Integer getMovie() {
        return movie;
    }

    public void setMovie(Integer movie) {
        this.movie = movie;
    }

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }
}
