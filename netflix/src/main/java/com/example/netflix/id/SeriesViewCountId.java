package com.example.netflix.id;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class SeriesViewCountId implements Serializable {

    private Integer userId;
    private Integer seriesId;
    private Integer episodeId;

    public SeriesViewCountId() {
    }

    public SeriesViewCountId(Integer userId, Integer seriesId, Integer episodeId) {
        this.userId = userId;
        this.seriesId = seriesId;
        this.episodeId = episodeId;
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

    public Integer getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Integer episodeId) {
        this.episodeId = episodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesViewCountId that = (SeriesViewCountId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(seriesId, that.seriesId) &&
                Objects.equals(episodeId, that.episodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, seriesId, episodeId);
    }
}
