package com.example.netflix.entity;

import com.example.netflix.id.GenreForUserId;
import jakarta.persistence.*;

@Entity
@Table(name = "genreforuser")
@IdClass(GenreForUserId.class)
public class GenreForUser {

    @Id
    @Column(name = "account_id")
    private Integer accountId;

    @Id
    @Column(name = "genre_id")
    private Integer genreId;

    // Getters and Setters
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

}
