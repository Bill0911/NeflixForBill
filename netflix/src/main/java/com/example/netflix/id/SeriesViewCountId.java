package com.example.netflix.id;

import com.example.netflix.entity.Movie;
import com.example.netflix.entity.Series;
import com.example.netflix.entity.User;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public class SeriesViewCountId implements Serializable
{
    private User user;
    private Series series;

    public SeriesViewCountId(User user, Series series)
    {
        this.user = user;
        this.series = series;
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
