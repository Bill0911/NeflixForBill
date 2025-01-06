package com.example.netflix.dto;

public class PersonalizedOfferItem
{
    private int id;
    private String title;

    public PersonalizedOfferItem(int id, String title, int genreId, int genreId1)
    {
        this.id = id;
        this.title = title;
    }

    public PersonalizedOfferItem()
    {
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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
