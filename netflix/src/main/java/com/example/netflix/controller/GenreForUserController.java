package com.example.netflix.controller;

import com.example.netflix.entity.GenreForUser;
import com.example.netflix.entity.ResponseItem;
import com.example.netflix.service.GenreForUserService;
import com.example.netflix.service.GenreForUserService;
import com.example.netflix.service.GenreService;
import com.example.netflix.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user/{accountId}/genre", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class GenreForUserController {
    private GenreService genreService;
    private UserService userService;
    private GenreForUserService genreForUserService;

    public GenreForUserController(GenreService genreService, UserService userService, GenreForUserService genreForUserService) {
        this.genreService = genreService;
        this.userService = userService;
        this.genreForUserService = genreForUserService;
    }

    @PostMapping("/{genreId}")
    public ResponseEntity<Object> addGenreForUser(@PathVariable Integer genreId, @PathVariable Integer accountId) {
        try {
            genreForUserService.addGenreForUser(genreId, accountId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseItem("Relation has been created", HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseItem.errorCheckForRelationItemsPOST(e.getMessage());
        }
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Object> getGenreForUser(@PathVariable Integer genreId, @PathVariable Integer accountId) {
        if (genreForUserService.getGenreForUser(genreId, accountId) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No such relation found");
        }
        return ResponseEntity.ok("Genre " + genreId + " - User " + accountId + " relation exists");
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Object> deleteGenreForUser(@PathVariable Integer genreId, @PathVariable Integer accountId) {
        try {
            genreForUserService.deleteGenreForUser(genreId, accountId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseItem("Deletion success", HttpStatus.ACCEPTED));
        } catch (Exception e) {
            if (e.getMessage().contains("Deletion failed. Item does not exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseItem("Custom error: Deletion failed. Item does not exist", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + e.getMessage());
        }
    }
}
