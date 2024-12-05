package com.example.netflix.entity;

import com.example.netflix.id.SeriesViewCountId;
import jakarta.persistence.*;
@Entity
@Table(name="seriesviewcount")
@IdClass(SeriesViewCountId.class)
public class SeriesViewCount {

    @Id
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @Column(nullable = false)
    private int number;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void incrementViewCount()
    {
        this.number++;
    }

    
}
