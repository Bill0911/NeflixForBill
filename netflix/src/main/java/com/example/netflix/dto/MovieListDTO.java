package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "movies")
public class MovieListDTO {
    private List<MovieDTO> movies;

    public MovieListDTO() {}
    public MovieListDTO(List<MovieDTO> movies) { this.movies = movies; }

    public List<MovieDTO> getMovies() { return movies; }
    public void setMovies(List<MovieDTO> movies) { this.movies = movies; }
}
