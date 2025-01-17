package com.example.netflix.dto;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "genre_total_views")
@Subselect("SELECT * FROM genre_total_views")
public class GenreViewCount implements Serializable{

    @Id
    private Integer genreId;

    private String genreName;
    private Long totalViews;

    public GenreViewCount(String genreName, Long totalViews) {
        this.genreName = genreName;
        this.totalViews = totalViews;
    }

    public GenreViewCount() {

    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(Long totalViews) {
        this.totalViews = totalViews;
    }
}