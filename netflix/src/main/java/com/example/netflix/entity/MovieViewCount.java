package com.example.netflix.entity;

import com.example.netflix.id.MovieViewCountId;
import jakarta.persistence.*;

@Entity
@Table(name = "movieviewcount")
@IdClass(MovieViewCountId.class)  // Use the composite key class
public class MovieViewCount {

    @Id
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private int number;

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void incrementViewCount() {
        this.number++;
    }
}


