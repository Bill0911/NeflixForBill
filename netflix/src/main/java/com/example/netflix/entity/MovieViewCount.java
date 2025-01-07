package com.example.netflix.entity;

import com.example.netflix.id.MovieViewCountId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movieviewcount")
@IdClass(MovieViewCountId.class)
public class MovieViewCount {


    @Column(name = "account_id", nullable = false)
    private Integer user;

    @Id
    @Column(name = "movie_id", nullable = false)
    private Integer movie;


    @Column(name = "number", nullable = false)
    private Integer number;

    @Column
    private LocalDateTime lastViewed;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void incrementViewCount() {
        this.number++;
    }

    public LocalDateTime getLastViewed()
    {
        return lastViewed;
    }

    public void setLastViewed(LocalDateTime lastViewed)
    {
        this.lastViewed = lastViewed;
    }
}
