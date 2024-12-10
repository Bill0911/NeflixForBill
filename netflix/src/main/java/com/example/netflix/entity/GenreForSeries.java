package com.example.netflix.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "genreforseries")
public class GenreForSeries
{
    @Id
    @OneToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Id
    @OneToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    public Genre getGenre ()
    {
        return genre;
    }

    public void setGenre (Genre genre)
    {
        this.genre = genre;
    }

    public Series getSeries ()
    {
        return series;
    }

    public void setSeries (Series series)
    {
        this.series = series;
    }
}