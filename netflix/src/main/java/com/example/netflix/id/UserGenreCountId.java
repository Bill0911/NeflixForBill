package com.example.netflix.id;

import java.io.Serializable;
import java.util.Objects;

public class UserGenreCountId implements Serializable {
    private Integer userId;
    private Integer genreId;

    public UserGenreCountId() {}

    public UserGenreCountId(Integer userId, Integer genreId) {
        this.userId = userId;
        this.genreId = genreId;
    }

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    // equals() and hashCode() for composite key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGenreCountId that = (UserGenreCountId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, genreId);
    }
}