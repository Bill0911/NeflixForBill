package com.example.netflix.id;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.entity.Series;
import com.example.netflix.entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GenreForSeriesId implements Serializable
{
    @OneToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToOne
    @JoinColumn(name = "series_id")
    private Series series;

    public GenreForSeriesId(Genre genre, Series series)
    {
        this.genre = genre;
        this.series = series;
    }

    public GenreForSeriesId ()
    {

    }

    public Genre getGenre ()
    {
        return genre;
    }

    public void setGenre (Genre genre)
    {
        this.genre = genre;
    }

    public Series getSeries ()
    {
        return series;
    }

    public void setSeries (Series series)
    {
        this.series = series;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreForSeriesId that = (GenreForSeriesId) o;
        return Objects.equals(genre.getGenreId(), that.genre.getGenreId()) &&
                Objects.equals(series.getSeriesId(), that.series.getSeriesId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(genre.getGenreId(), series.getSeriesId());
    }
}
