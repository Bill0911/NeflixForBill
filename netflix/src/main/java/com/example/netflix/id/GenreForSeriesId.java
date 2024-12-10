package com.example.netflix.id;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class GenreForSeriesId implements Serializable {

    private Integer genreId;
    private Integer seriesId;

    public GenreForSeriesId() {
    }

    public GenreForSeriesId(Integer genreId, Integer seriesId) {
        this.genreId = genreId;
        this.seriesId = seriesId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
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
        GenreForSeriesId that = (GenreForSeriesId) o;
        return Objects.equals(genreId, that.genreId) && Objects.equals(seriesId, that.seriesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, seriesId);
    }
}