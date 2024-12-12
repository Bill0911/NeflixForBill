package com.example.netflix.dto;

public class GenreSeriesRequest
{
    private Integer genreId;
    private Integer seriesId;

    public Integer getGenreId ()
    {
        return genreId;
    }

    public void setGenreId (Integer genreId)
    {
        this.genreId = genreId;
    }

    public Integer getSeriesId ()
    {
        return seriesId;
    }

    public void setSeriesId (Integer seriesId)
    {
        this.seriesId = seriesId;
    }
}
