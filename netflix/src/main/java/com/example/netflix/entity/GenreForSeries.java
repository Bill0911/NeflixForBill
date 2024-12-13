package com.example.netflix.entity;

import com.example.netflix.id.GenreForSeriesId;
import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private Genre genre;

    //I set insertable and updatable to FALSE because
    //    // These Ids cannot be changed directly in this table,
    // It should be updated in the table Series or Genre,
    @ManyToOne
    @JoinColumn(name = "series_id", insertable = false, updatable = false)
    private Series series;

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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}