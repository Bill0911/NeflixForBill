package com.example.netflix.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GenreForUserId implements Serializable {

    private Integer accountId;  // Use primary key type directly
    private Integer genreId; // Use primary key type directly

    public GenreForUserId() {}

    public GenreForUserId(Integer accountId, Integer genreId) {
        this.accountId = accountId;
        this.genreId = genreId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, genreId);
    }
}
