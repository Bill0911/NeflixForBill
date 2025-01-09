package com.example.netflix.entity;

import com.example.netflix.id.GenreForSeriesId;
import jakarta.persistence.*;


@Entity
@Table(name = "genreforseries")
@IdClass(GenreForSeriesId.class)
public class GenreForSeries {
    @Id
    @Column(name = "genre_id")
    private Integer genreId;

    @Id
    @Column(name = "series_id")
    private Integer seriesId;

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }
}