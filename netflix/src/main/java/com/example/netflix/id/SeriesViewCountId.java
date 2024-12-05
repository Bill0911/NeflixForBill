package com.example.netflix.id;

import com.example.netflix.entity.Series;
import com.example.netflix.entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SeriesViewCountId implements Serializable
{
    @ManyToOne
    @JoinColumn(name="account_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="series_id")
    private Series series;

    public SeriesViewCountId(User user, Series series)
    {
        this.user = user;
        this.series = series;
    }

    public SeriesViewCountId() {

    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Series getSeries()
    {
        return series;
    }

    public void setSeries(Series series)
    {
        this.series = series;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesViewCountId that = (SeriesViewCountId) o;
        return Objects.equals(user.getAccountId(), that.user.getAccountId()) &&
                Objects.equals(series.getSeriesId(), that.series.getSeriesId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(user.getAccountId(), series.getSeriesId());
    }

}
