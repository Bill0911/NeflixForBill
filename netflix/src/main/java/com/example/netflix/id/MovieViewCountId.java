package com.example.netflix.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieViewCountId implements Serializable {
    private Integer accountId;
    private Integer movieId;

    // Default constructor
    public MovieViewCountId() {}

    // Constructor with fields
    public MovieViewCountId(Integer accountId, Integer movieId) {
        this.accountId = accountId;
        this.movieId = movieId;
    }

    // Getters and Setters
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieViewCountId that = (MovieViewCountId) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, movieId);
    }
}
