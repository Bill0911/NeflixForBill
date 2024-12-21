package com.example.netflix.controller;

import com.example.netflix.entity.GenreForUser;
import com.example.netflix.service.GenreForUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre-for-user")
public class GenreForUserController {

    private final GenreForUserService genreForUserService;

    public GenreForUserController(GenreForUserService genreForUserService) {
        this.genreForUserService = genreForUserService;
    }

    @GetMapping
    public ResponseEntity<List<GenreForUser>> getAllGenreForUsers() {
        return ResponseEntity.ok(genreForUserService.getAllGenreForUsers());
    }

    @GetMapping("/{accountId}/{genreId}")
    public ResponseEntity<GenreForUser> getGenreForUserById(@PathVariable Integer accountId, @PathVariable Integer genreId) {
        return ResponseEntity.ok(genreForUserService.getGenreForUserById(accountId, genreId));
    }

    @PostMapping
    public ResponseEntity<GenreForUser> addGenreForUser(@RequestBody GenreForUser genreForUser) {
        return ResponseEntity.ok(genreForUserService.addGenreForUser(genreForUser));
    }

    @PutMapping
    public ResponseEntity<GenreForUser> updateGenreForUser(@RequestBody GenreForUser genreForUser) {
        return ResponseEntity.ok(genreForUserService.updateGenreForUser(genreForUser));
    }

    @DeleteMapping("/{accountId}/{genreId}")
    public ResponseEntity<String> deleteGenreForUser(@PathVariable Integer accountId, @PathVariable Integer genreId) {
        genreForUserService.deleteGenreForUser(accountId, genreId);
        return ResponseEntity.ok("GenreForUser deleted successfully");
    }

    @PatchMapping("/{accountId}/{genreId}")
    public ResponseEntity<GenreForUser> patchGenreForUser(
            @PathVariable Integer accountId,
            @PathVariable Integer genreId,
            @RequestBody GenreForUser patchData) {
        return ResponseEntity.ok(genreForUserService.patchGenreForUser(accountId, genreId, patchData));
    }
}
