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

    private String title;

    public MovieDTO(String title)
    {
        this.title = title;
    }

    public MovieDTO() {

    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getMovieId() {
        return movieId;
    }
}