package com.example.netflix.id;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class SeriesViewCountId implements Serializable {

    private Integer userId;
    private Integer seriesId;

    public SeriesViewCountId() {
    }

    public SeriesViewCountId(Integer userId, Integer seriesId) {
        this.userId = userId;
        this.seriesId = seriesId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesViewCountId that = (SeriesViewCountId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(seriesId, that.seriesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, seriesId);
    }
}
