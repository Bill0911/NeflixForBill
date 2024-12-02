package com.example.netflix.id;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.User;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public class MovieViewCountId implements Serializable {

    private User user;
    private Movie movie;

    // Default constructor
    public MovieViewCountId() {}

    // Constructor with fields


    public MovieViewCountId(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
    }

    // Getters and Setters


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieViewCountId that = (MovieViewCountId) o;
        return Objects.equals(user.getAccountId(), that.user.getAccountId()) &&
                Objects.equals(movie.getMovieId(), that.movie.getMovieId());
    }

    // hashCode() method: generate hash code based on accountId and movieId
    @Override
    public int hashCode() {
        return Objects.hash(user.getAccountId(), movie.getMovieId());
    }
}
