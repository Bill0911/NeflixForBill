package com.example.netflix.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "genres_without_movie")
public class GenreWithoutMovieEntity {

    @Id
    private Integer genreId;
    private String genreName;

    public GenreWithoutMovieEntity() {}

    public Integer getGenreId() { return genreId; }
    public void setGenreId(Integer genreId) { this.genreId = genreId; }

    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }
}
