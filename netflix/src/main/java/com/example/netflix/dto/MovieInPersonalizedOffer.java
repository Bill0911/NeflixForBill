package com.example.netflix.dto;

public class MovieInPersonalizedOffer
{
    private int movieId;
    private String title;

    public MovieInPersonalizedOffer(int movieId, String title, int genreId, int genreId1)
    {
        this.movieId = movieId;
        this.title = title;
    }

    public MovieInPersonalizedOffer()
    {
    }

    public int getMovieId()
    {
        return movieId;
    }

    public void setMovieId(int movieId)
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
