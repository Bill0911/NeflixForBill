package com.example.netflix.dto;

public class GenreDTO
{
    private String genreName;

    public GenreDTO(String genreName)
    {
        this.genreName = genreName;
    }

    public String getGenreName()
    {
        return genreName;
    }

    public void setGenreName(String genreName)
    {
        this.genreName = genreName;
    }
}