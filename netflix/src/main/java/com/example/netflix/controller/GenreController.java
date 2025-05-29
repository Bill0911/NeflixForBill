package com.example.netflix.controller;

import com.example.netflix.dto.GenreDTO;
import com.example.netflix.dto.GenreListDTO;
import com.example.netflix.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(
        value = "/api/genres",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<GenreListDTO> getAllGenres() {
        return ResponseEntity.ok(new GenreListDTO(genreService.getAllGenres()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Integer id) {
        return genreService.getGenreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> addGenre(@RequestBody GenreDTO genre) {
        genreService.addGenre(genre.getGenreName());
        return ResponseEntity.ok("New genre '" + genre.getGenreName() + "' added");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenre(@PathVariable Integer id, @RequestBody GenreDTO genre) {
        genreService.updateGenre(id, genre.getGenreName());
        return ResponseEntity.ok("Genre id " + id + " has been renamed to " + genre.getGenreName());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchGenre(@PathVariable Integer id, @RequestBody GenreDTO genre) {
        genreService.updateGenre(id, genre.getGenreName());
        return ResponseEntity.ok("Genre " + id + " has been renamed to " + genre.getGenreName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Integer id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok("Genre deleted successfully.");
    }

    @GetMapping("/without-movie")
    public ResponseEntity<GenreListDTO> getGenresWithoutMovie(@RequestParam(name = "hasMovies", required = false) Boolean hasMovies) {
        if (!hasMovies) {
            return ResponseEntity.ok(new GenreListDTO(genreService.getGenresWithoutMovie()));
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_IMPLEMENTED,
                    "Filtering genres with movies is not implemented"
            );
        }
    }

    @GetMapping("/statistics/views")
    public ResponseEntity<Object> getGenresViewCounts() {
        return ResponseEntity.ok(genreService.getViewCounts());
    }
}
