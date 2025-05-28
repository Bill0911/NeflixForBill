package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "series")
public class SeriesDTO {
    private Integer seriesId;
    private String title;
    private Integer minimumAge;

    public SeriesDTO() {}

    public SeriesDTO(Integer seriesId, String title, Integer minimumAge) {
        this.seriesId = seriesId;
        this.title = title;
        this.minimumAge = minimumAge;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }
}
