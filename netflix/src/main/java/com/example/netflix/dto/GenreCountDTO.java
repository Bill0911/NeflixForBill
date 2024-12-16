package com.example.netflix.dto;

public class GenreCountDTO {

    private String genreName;
    private Long totalViews;

    public GenreCountDTO(String genreName, Long totalViews) {
        this.genreName = genreName;
        this.totalViews = totalViews;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(Long totalViews) {
        this.totalViews = totalViews;
    }
}