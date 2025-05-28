package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "genre")
public class GenreDTO {

    private Integer genreId;
    private String genreName;

    public GenreDTO() {}

    public GenreDTO(Integer genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public Integer getGenreId() {
        return genreId;
    }
    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
    public String getGenreName() {
        return genreName;
    }
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
