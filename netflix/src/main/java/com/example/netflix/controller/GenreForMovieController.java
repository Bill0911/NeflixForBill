package com.example.netflix.controller;

import com.example.netflix.dto.GenreForMovieDTO;
import com.example.netflix.service.GenreForMovieService;
import com.example.netflix.service.GenreService;
import com.example.netflix.service.MovieService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/genre-for-movie",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class GenreForMovieController {
    private final GenreService genreService;
    private final MovieService movieService;
    private final GenreForMovieService genreForMovieService;

    public GenreForMovieController(GenreService genreService, MovieService movieService, GenreForMovieService genreForMovieService) {
        this.genreService = genreService;
        this.movieService = movieService;
        this.genreForMovieService = genreForMovieService;
    }

    @PostMapping("/{genreId}/{movieId}")
    public ResponseEntity<String> addGenreForMovie(@PathVariable Integer genreId, @PathVariable Integer movieId) {
        genreService.getGenreById(genreId).orElseThrow(() -> new RuntimeException("Genre not found"));
        movieService.getMovieById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));
        genreForMovieService.addGenreForMovie(genreId, movieId);
        return ResponseEntity.ok("Genre-Movie relation has been created");
    }

    @GetMapping("/{genreId}/{movieId}")
    public ResponseEntity<GenreForMovieDTO> getGenreForMovie(@PathVariable Integer genreId, @PathVariable Integer movieId) {
        GenreForMovieDTO dto = genreForMovieService.getGenreForMovie(genreId, movieId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<GenreForMovieDTO>> getManyGenreForMovie() {
        return ResponseEntity.ok(genreForMovieService.getManyGenreForMovies());
    }

    @DeleteMapping("/{genreId}/{movieId}")
    public ResponseEntity<String> deleteGenreForMovie(@PathVariable Integer genreId, @PathVariable Integer movieId) {
        genreForMovieService.deleteGenreForMovie(genreId, movieId);
        return ResponseEntity.ok("Genre-Movie relation has been deleted");
    }

}
