package com.example.netflix.controller;

import com.example.netflix.entity.Movie;
import com.example.netflix.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController
{

    private final MovieService movieService;

    public MovieController(MovieService movieService)
    {
        this.movieService = movieService;
    }

    // GET: Retrieve all movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies()
    {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    // GET: Retrieve a movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id)
    {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    // POST: Create a new movie
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie)
    {
        Movie createdMovie = movieService.createMovie(movie);
        return ResponseEntity.ok(createdMovie);
    }

    // PUT: Update an existing movie
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Integer id, @RequestBody Movie updatedMovie)
    {
        Movie updated = movieService.updateMovie(id, updatedMovie);
        return ResponseEntity.ok(updated);
    }

    // DELETE: Delete a movie by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Integer id)
    {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully.");
    }


}
