package com.example.netflix.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Integer genreId;

    @Column(name = "genre_name", nullable = false, unique = true)
    private String genreName;

    public Genre(Integer genreId, String genreName)
    {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public Genre()
    {

    }

    public Integer getGenreId()
    {
        return this.genreId;
    }

    public void setGenreId(Integer genreId)
    {
        this.genreId = genreId;
    }

    public String getGenreName()
    {
        return this.genreName;
    }

    public void setGenreName(String genreName)
    {
        this.genreName = genreName;
    }
}
