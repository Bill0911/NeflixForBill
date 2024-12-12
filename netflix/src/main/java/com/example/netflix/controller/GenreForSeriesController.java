package com.example.netflix.controller;

import com.example.netflix.dto.GenreSeriesRequest;
import com.example.netflix.dto.GenreSeriesRequest;
import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.service.GenreForSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre-for-series")
public class GenreForSeriesController
{

    @Autowired
    private GenreForSeriesService genreForSeriesService;

    @PostMapping
    public ResponseEntity<GenreForSeries> createGenreForSeries(@RequestBody GenreSeriesRequest genreSeriesRequest)
    {
        GenreForSeries genreForSeries = new GenreForSeries();
        genreForSeries.setGenreId(genreSeriesRequest.getGenreId());
        genreForSeries.setSeriesId(genreSeriesRequest.getSeriesId());
        GenreForSeries createdGenreForSeries = genreForSeriesService.save(genreForSeries);
        return ResponseEntity.ok(createdGenreForSeries);
    }
    @GetMapping("/{genreId}/{seriesId}")
    public ResponseEntity<GenreForSeries> getGenreForSeries(@PathVariable Integer genreId, @PathVariable Integer seriesId)
    {
        GenreForSeriesId targetId = new GenreForSeriesId(genreId, seriesId);
        GenreForSeries genreForSeries = genreForSeriesService.findById(targetId);
        return ResponseEntity.ok(genreForSeries);
    }

    @GetMapping
    public ResponseEntity<List<GenreForSeries>> getAllGenreForSeries()
    {
        List<GenreForSeries> genreForSeriesList = genreForSeriesService.findAll();
        return ResponseEntity.ok(genreForSeriesList);
    }

    @PutMapping("/{genreId}/{seriesId}")
    public ResponseEntity<GenreForSeries> updateGenreForSeries(@PathVariable Integer genreId, @PathVariable Integer seriesId, @RequestBody GenreSeriesRequest genreSeriesRequest)
    {
        GenreForSeriesId id = new GenreForSeriesId(genreId, seriesId);
        GenreForSeries genreForSeries = new GenreForSeries();
        genreForSeries.setGenreId(genreSeriesRequest.getGenreId());
        genreForSeries.setSeriesId(genreSeriesRequest.getSeriesId());
        GenreForSeries updatedGenreForSeries = genreForSeriesService.update(id, genreForSeries);
        return ResponseEntity.ok(updatedGenreForSeries);
    }

    @DeleteMapping("/{genreId}/{seriesId}")
    public ResponseEntity<Void> deleteGenreForSeries(@PathVariable Integer genreId, @PathVariable Integer seriesId)
    {
        GenreForSeriesId id = new GenreForSeriesId(genreId, seriesId);
        genreForSeriesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

