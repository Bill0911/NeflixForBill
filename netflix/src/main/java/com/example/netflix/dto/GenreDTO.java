package com.example.netflix.dto;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "genres_without_movie")
@Subselect("SELECT * FROM genres_without_movie")
public class GenreDTO implements Serializable
{

    @Id
    private Long genreId;

    private String genreName;

    public GenreDTO(String genreName)
    {
        this.genreName = genreName;
    }

    public GenreDTO() {

    }

    public String getGenreName()
    {
        return genreName;
    }

    public void setGenreName(String genreName)
    {
        this.genreName = genreName;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getGenreId() {
        return genreId;
    }
}