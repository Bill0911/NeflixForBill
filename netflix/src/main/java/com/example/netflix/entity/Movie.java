package com.example.netflix.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "movie")
public class Movie
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer movieId;

    @Column(nullable = false)
    private String title;

    @Column(name = "duration", columnDefinition = "TIME DEFAULT '00:00:00'")
    private LocalTime duration;

    @Column(name = "sd_available")
    private Boolean sdAvailable;

    @Column(name = "hd_available")
    private Boolean hdAvailable;

    @Column(name = "uhd_available")
    private Boolean uhdAvailable;

    @Column(name = "minimum_age", nullable = false)
    private Integer minimumAge;

    public Movie()
    {
        this.duration = LocalTime.of(0, 0, 0); // Default value
    }

    public Movie(String title, LocalTime duration, Integer minimumAge)
    {
        this.title = title;
        this.duration = duration != null ? duration : LocalTime.of(0, 0, 0);
        this.minimumAge = minimumAge;
    }

    public Integer getMovieId()
    {
        return movieId;
    }

    public void setMovieId(Integer movieId)
    {
        this.movieId = movieId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public LocalTime getDuration()
    {
        return duration;
    }

    public void setDuration(LocalTime duration)
    {
        this.duration = duration;
    }

    public Integer getMinimumAge()
    {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge)
    {
        this.minimumAge = minimumAge;
    }

    public Boolean isSdAvailable()
    {
        return sdAvailable;
    }

    public void setSdAvailable(Boolean sdAvailable)
    {
        this.sdAvailable = sdAvailable;
    }

    public Boolean isHdAvailable()
    {
        return hdAvailable;
    }

    public void setHdAvailable(Boolean hdAvailable)
    {
        this.hdAvailable = hdAvailable;
    }

    public Boolean isUhdAvailable()
    {
        return uhdAvailable;
    }

    public void setUhdAvailable(Boolean uhdAvailable)
    {
        this.uhdAvailable = uhdAvailable;
    }
}
