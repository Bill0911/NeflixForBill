package com.example.netflix.entity;

import com.example.netflix.id.MovieViewCountId;
import jakarta.persistence.*;

@Entity
@Table(name="movieviewcount")
public class MovieViewCount {
    

    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false, nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private int number;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
