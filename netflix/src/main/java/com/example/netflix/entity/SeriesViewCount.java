package com.example.netflix.entity;

import com.example.netflix.id.SeriesViewCountId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seriesviewcount")
@IdClass(SeriesViewCountId.class)
public class SeriesViewCount {

    @Id
    @Column(name = "account_id", insertable = false, updatable = false)
    private Integer user;

    @Id
    @Column(name = "series_id", insertable = false, updatable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer number;

    @Column(name = "last_viewed")
    private LocalDateTime lastViewed;

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDateTime getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(LocalDateTime lastViewed) {
        this.lastViewed = lastViewed;
    }

    public void incrementViewCount() {
        this.number++;
        this.lastViewed = LocalDateTime.now();
    }
}
