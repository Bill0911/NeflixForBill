package com.example.netflix.entity;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name="series")
public class Series
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "series_id")
    private Integer seriesId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_range")
    private Integer ageRange;

    public Integer getSeriesId()
    {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId)
    {
        this.seriesId = seriesId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getAgeRange()
    {
        return ageRange;
    }

    public void setAgeRange(Integer ageRange)
    {
        this.ageRange = ageRange;
    }
}
