package com.example.netflix.controller;

import com.example.netflix.dto.PersonalizedOfferItem;
import com.example.netflix.entity.Movie;
import com.example.netflix.entity.Series;
import com.example.netflix.entity.UserGenreCount;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PersonalizedOfferController {
    private final PersonalizedOfferService personalizedOfferService;
    private final JwtUtil jwtUtil;

    public PersonalizedOfferController(PersonalizedOfferService personalizedOfferService, JwtUtil jwtUtil) {
        this.personalizedOfferService = personalizedOfferService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/genres-watched-count")
    public ResponseEntity<List<UserGenreCount>> getUserGenreWatchCounts(@RequestHeader("Authorization") String token) {
        Integer userId = jwtUtil.extractId(token.substring(7));
        return ResponseEntity.ok(personalizedOfferService.getUserGenreCounts(userId));
    }

    @GetMapping("/personalized-offer")
    public List<PersonalizedOfferItem> getPersonalizedOffer(@RequestHeader("Authorization") String token) {
        Integer userId = jwtUtil.extractId(token.substring(7));
        System.out.println("The results are NOT YET retrieved");
        return personalizedOfferService.getPersonalizedOfferMovies(userId, 20);
    }
}