package com.example.netflix.id;

import com.example.netflix.entity.MoviesProfileWatchlist;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SeriesProfileWatchlistId implements Serializable {
    private Integer profile;
    private Integer series;

    // Default constructor
    public SeriesProfileWatchlistId() {}

    // Parameterized constructor
    public SeriesProfileWatchlistId(Integer profile, Integer series) {
        this.profile = profile;
        this.series = series;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesProfileWatchlistId that = (SeriesProfileWatchlistId) o;
        return Objects.equals(profile, that.profile) &&
                Objects.equals(series, that.series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profile, series);
    }
}
