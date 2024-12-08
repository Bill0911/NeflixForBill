package com.example.netflix.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_Id")
    private Integer genreId;

    @Column(nullable = false)
    private String name;

    @Column(name = "genre_name")
    private String genreName;

    public Genre(Integer genreId, String name, String genreName)
    {
        this.genreId = genreId;
        this.name = name;
        this.genreName = genreName;
    }

    public Integer getGenreId()
    {
        return this.genreId;
    }

    public void setGenreId(Integer genreId)
    {
        this.genreId = genreId;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
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
