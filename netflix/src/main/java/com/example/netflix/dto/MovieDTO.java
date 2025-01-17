package com.example.netflix.dto;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "movies_without_genre")
@Subselect("SELECT * FROM movies_without_genre")
public class MovieDTO implements Serializable
{

    @Id
    private Long movieId;

    private String movieName;

    public MovieDTO(String movieName)
    {
        this.movieName = movieName;
    }

    public MovieDTO() {

    }

    public String getMovieName()
    {
        return movieName;
    }

    public void setMovieName(String movieName)
    {
        this.movieName = movieName;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getMovieId() {
        return movieId;
    }
}