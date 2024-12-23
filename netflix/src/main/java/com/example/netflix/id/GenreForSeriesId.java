package com.example.netflix.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GenreForSeriesId implements Serializable {

    private Integer genreId;
    private Integer seriesId;

    // Default constructor
    public GenreForSeriesId() {}

    // Parameterized constructor
    public GenreForSeriesId(Integer genreId, Integer seriesId) {
        this.genreId = genreId;
        this.seriesId = seriesId;
    }

    // Getters and setters
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

    // equals() method for comparing instances
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreForSeriesId that = (GenreForSeriesId) o;
        return Objects.equals(genreId, that.genreId) && Objects.equals(seriesId, that.seriesId);
    }

    // hashCode() method for proper hash-based collections
    @Override
    public int hashCode() {
        return Objects.hash(genreId, seriesId);
    }
}
