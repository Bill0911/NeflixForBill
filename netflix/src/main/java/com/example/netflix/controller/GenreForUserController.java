package com.example.netflix.controller;

import com.example.netflix.entity.GenreForUser;
import com.example.netflix.service.GenreForUserService;
import com.example.netflix.service.GenreForUserService;
import com.example.netflix.service.GenreService;
import com.example.netflix.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre-for-user")
public class GenreForUserController {
    private GenreService genreService;
    private UserService userService;
    private GenreForUserService genreForUserService;

    public GenreForUserController(GenreService genreService, UserService userService, GenreForUserService genreForUserService) {
        this.genreService = genreService;
        this.userService = userService;
        this.genreForUserService = genreForUserService;
    }

    @PostMapping("/{id1}/{id2}")
    public ResponseEntity<Object> addGenreForUser(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            genreService.getGenreById(id1);
            userService.getUserById(id2);
            genreForUserService.addGenreForUser(id1, id2);
            return ResponseEntity.ok("Genre - User relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<Object> getGenreForUser(@PathVariable Integer id1, @PathVariable Integer id2) {
        if (genreForUserService.getGenreForUser(id1, id2) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }

        return ResponseEntity.ok("Genre " + id1 + " - User " + id2 + " relation exists");
    }

    @GetMapping()
    public ResponseEntity<Object> getManyGenreForUser() {
        return ResponseEntity.ok(genreForUserService.getManyGenreForUsers());
    }

    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<Object> deleteGenreForUser(@PathVariable Integer id1, @PathVariable Integer id2) {
        try {
            genreForUserService.deleteGenreForUser(id1, id2);
            return ResponseEntity.ok("Genre - User relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/{id1}/{id2}/{newId1}/{newId2}")
    public ResponseEntity<Object> patchGenreForUser(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer newId1, @PathVariable Integer newId2) {
        try {
            if (newId1 == 0) {
                newId1 = null;
            }
            if (newId2 == 0) {
                newId2 = null;
            }
            genreForUserService.patchGenreForUser(id1, id2, newId1, newId2);
            return ResponseEntity.ok(id1 + " -> " + newId1 + " | " + id2 + " -> " + newId2);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id1}/{id2}/{newId1}/{newId2}")
    public ResponseEntity<Object> putGenreForUser(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer newId1, @PathVariable Integer newId2) {
        try {
            genreForUserService.updateGenreForUser(id1, id2, newId1, newId2);
            return ResponseEntity.ok(id1 + " -> " + newId1 + " | " + id2 + " -> " + newId2);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
