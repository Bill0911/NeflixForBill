package com.example.netflix.controller;

import com.example.netflix.dto.MovieDTO;
import com.example.netflix.dto.MovieListDTO;
import com.example.netflix.service.MovieService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/api/movies",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<MovieListDTO> getAllMovies() {
        return ResponseEntity.ok(new MovieListDTO(movieService.getManyMovies()));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> addMovie(@RequestBody MovieDTO movieDTO) {
        movieService.addMovie(movieDTO);
        return ResponseEntity.ok("Movie has been created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable Integer id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok("Movie deleted successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchMovieById(@PathVariable Integer id, @RequestBody MovieDTO movieDTO) {
        movieService.patchMovieById(id, movieDTO);
        return ResponseEntity.ok("Movie has been patched successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putMovieById(@PathVariable Integer id, @RequestBody MovieDTO updatedMovieDTO) {
        movieService.updateMovieById(id, updatedMovieDTO);
        return ResponseEntity.ok("Movie has been updated successfully");
    }

    @GetMapping("/without-genre")
    public ResponseEntity<MovieListDTO> getMoviesWithoutGenre() {
        return ResponseEntity.ok(new MovieListDTO(movieService.getMoviesWithoutGenre()));
    }
}
