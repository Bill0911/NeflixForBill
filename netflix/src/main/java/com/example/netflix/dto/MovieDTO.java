package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "movie")
public class MovieDTO implements Serializable {

    private Integer movieId;
    private String title;

    public MovieDTO() {}

    public MovieDTO(Integer movieId, String title)
    {
        this.movieId = movieId;
        this.title = title;
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
}
