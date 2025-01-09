package com.example.netflix.entity;

import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.id.MoviesProfileWatchlistId;
import com.example.netflix.id.SeriesProfileWatchlistId;
import jakarta.persistence.*;

@Entity
@Table(name = "moviesprofilewatchlist")
@IdClass(MoviesProfileWatchlistId.class)
public class MoviesProfileWatchlist {

    @Id
    @Column(name = "profile_id", insertable = false, updatable = false)
    private Integer profile;

    @Id
    @Column(name = "movie_id", insertable = false, updatable = false)
    private Integer movie;

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }

    public Integer getMovie() {
        return movie;
    }

    public void setMovie(Integer movie) {
        this.movie = movie;
    }
}
