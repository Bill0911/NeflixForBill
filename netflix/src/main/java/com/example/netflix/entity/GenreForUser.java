package com.example.netflix.entity;

import com.example.netflix.id.GenreForUserId;
import jakarta.persistence.*;

@Entity
@Table(name = "genreforuser")
@IdClass(GenreForUserId.class) // Use the composite key class
public class GenreForUser {

    @Id
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Id
    @Column(name = "genre_id", nullable = false)
    private Integer genreId;

    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private Genre genre;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
