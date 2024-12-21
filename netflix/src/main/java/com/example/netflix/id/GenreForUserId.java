package com.example.netflix.id;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.User;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GenreForUserId implements Serializable {

    private Integer userId;  // Use primary key type directly
    private Integer genreId; // Use primary key type directly

    public GenreForUserId() {}

    public GenreForUserId(Integer userId, Integer genreId) {
        this.userId = userId;
        this.genreId = genreId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreForUserId that = (GenreForUserId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, genreId);
    }
}
