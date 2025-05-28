package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;

@XmlRootElement(name = "episode")
public class EpisodeDTO {
    private Integer episodeId;
    private String title;
    private LocalTime duration;
    private Integer series;

    public EpisodeDTO() {}

    public EpisodeDTO(Integer episodeId, String title, LocalTime duration, Integer series) {
        this.episodeId = episodeId;
        this.title = title;
        this.duration = duration;
        this.series = series;
    }

    public Integer getEpisodeId()
    {
        return episodeId;
    }

    public void setEpisodeId(Integer episodeId)
    {
        this.episodeId = episodeId;
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

    public Integer getSeries()
    {
        return series;
    }

    public void setSeries(Integer series)
    {
        this.series = series;
    }
}
