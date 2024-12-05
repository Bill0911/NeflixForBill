package com.example.netflix.entity;
import jakarta.persistence.*;

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

    @Column(name = "minimum_age", nullable = false)
    private Integer minimum_age;

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

    public Integer getMinimum_age()
    {
        return minimum_age;
    }

    public void setMinimum_age(Integer ageRange)
    {
        this.minimum_age = ageRange;
    }
}
