package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "genreForMovie")
public class GenreForMovieDTO {
    private Integer genreId;
    private Integer movieId;

    public GenreForMovieDTO() {}

    public GenreForMovieDTO(Integer genreId, Integer movieId) {
        this.genreId = genreId;
        this.movieId = movieId;
    }

    public Integer getGenreId() { return genreId; }
    public void setGenreId(Integer genreId) { this.genreId = genreId; }
    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }
}
