package com.example.netflix.id;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class SeriesViewCountId implements Serializable {

    private Integer user;
    private Integer series;

    public SeriesViewCountId() {
    }

    public SeriesViewCountId(Integer user, Integer series) {
        this.user = user;
        this.series = series;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesViewCountId that = (SeriesViewCountId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(series, that.series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, series);
    }
}
