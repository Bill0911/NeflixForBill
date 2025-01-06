package com.example.netflix.entity;

import com.example.netflix.id.SeriesViewCountId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seriesviewcount")
@IdClass(SeriesViewCountId.class)
public class SeriesViewCount {

    @Id
    @Column(name = "account_id")
    private Integer userId;

    @Id
    @Column(name = "series_id")
    private Integer seriesId;

    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Integer user;

    @JoinColumn(name = "series_id", insertable = false, updatable = false)
    private Integer series;

    @Column(nullable = false)
    private int number;

    @Column(name = "last_viewed")
    private LocalDateTime lastViewed;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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
