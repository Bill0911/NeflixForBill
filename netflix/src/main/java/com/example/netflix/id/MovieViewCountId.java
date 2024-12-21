package com.example.netflix.id;

import java.io.Serializable;
import java.util.Objects;

public class MovieViewCountId implements Serializable {

    private Integer user; // Referencing account_id from User
    private Integer movie; // Referencing movie_id from Movie

    public MovieViewCountId() {}

    public MovieViewCountId(Integer user, Integer movie) {
        this.user = user;
        this.movie = movie;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
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
        MovieViewCountId that = (MovieViewCountId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, movie);
    }
}
