package com.example.netflix.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MoviesProfileWatchlistId implements Serializable {
    private Integer profile;
    private Integer movie;

    // Default constructor
    public MoviesProfileWatchlistId() {}

    // Parameterized constructor
    public MoviesProfileWatchlistId(Integer profile, Integer movie) {
        this.profile = profile;
        this.movie = movie;
    }

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profileId) {
        this.profile = profileId;
    }

    public Integer getMovie() {
        return movie;
    }

    public void setMovie(Integer movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviesProfileWatchlistId that = (MoviesProfileWatchlistId) o;
        return Objects.equals(profile, that.profile) &&
                Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profile, movie);
    }
}
