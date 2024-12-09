package com.example.netflix.entity;

import com.example.netflix.id.GenreForUserId;
import jakarta.persistence.*;

@Entity
@Table(name = "genreforuser")
@IdClass(GenreForUserId.class)  // Use the composite key class
public class GenreForUser {

    @Id
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Genre getGenre()
    {
        return genre;
    }

    public void setGenre(Genre genre)
    {
        this.genre = genre;
    }
}


