package com.example.netflix.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.Set;

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
    private boolean sdAvailable;

    @Column(name = "hd_available")
    private boolean hdAvailable;

    @Column(name = "uhd_available")
    private boolean uhdAvailable;

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

    public boolean isSdAvailable()
    {
        return sdAvailable;
    }

    public void setSdAvailable(boolean sdAvailable)
    {
        this.sdAvailable = sdAvailable;
    }

    public boolean isHdAvailable()
    {
        return hdAvailable;
    }

    public void setHdAvailable(boolean hdAvailable)
    {
        this.hdAvailable = hdAvailable;
    }

    public boolean isUhdAvailable()
    {
        return uhdAvailable;
    }

    public void setUhdAvailable(boolean uhdAvailable)
    {
        this.uhdAvailable = uhdAvailable;
    }
}
