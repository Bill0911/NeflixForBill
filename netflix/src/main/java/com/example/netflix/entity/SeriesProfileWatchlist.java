package com.example.netflix.entity;

import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.id.SeriesProfileWatchlistId;
import jakarta.persistence.*;

@Entity
@Table(name = "seriesprofilewatchlist")
@IdClass(SeriesProfileWatchlistId.class)
public class SeriesProfileWatchlist {

    @Id
    @Column(name = "profile_id", insertable = false, updatable = false)
    private Integer profile;

    @Id
    @Column(name = "series_id", insertable = false, updatable = false)
    private Integer series;

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }
}
