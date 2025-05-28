package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "genres")
public class GenreListDTO {

    private List<GenreDTO> genres;

    public GenreListDTO() {}

    public GenreListDTO(List<GenreDTO> genres) {
        this.genres = genres;
    }

    public List<GenreDTO> getGenres() { return genres; }
    public void setGenres(List<GenreDTO> genres) { this.genres = genres; }
}
