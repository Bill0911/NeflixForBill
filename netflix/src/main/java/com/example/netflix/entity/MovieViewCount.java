package com.example.netflix.entity;

import com.example.netflix.id.MovieViewCountId;
import jakarta.persistence.*;

@Entity
@Table(name = "movieviewcount")
@IdClass(MovieViewCountId.class)
public class MovieViewCount {

    @Id
    @Column(name = "account_id", nullable = false)
    private Integer user;

    @Id
    @Column(name = "movie_id", nullable = false)
    private Integer movie;

    @Column(nullable = false)
    private int number;

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
