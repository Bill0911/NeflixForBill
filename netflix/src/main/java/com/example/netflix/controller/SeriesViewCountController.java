package com.example.netflix.controller;

import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.SeriesViewCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/series")
public class SeriesViewCountController {

    private final SeriesViewCountService seriesViewCountService;
    private final JwtUtil jwtUtil;

    public SeriesViewCountController(SeriesViewCountService seriesViewCountService, JwtUtil jwtUtil) {
        this.seriesViewCountService = seriesViewCountService;
        this.jwtUtil = jwtUtil;
    }

//    @PostMapping("/add-to-view-count")
//    public ResponseEntity<String> addToViewCount(@RequestParam Integer seriesId, @RequestHeader("Authorization") String token) {
//        String jwt = token.substring(7);
//        int id = jwtUtil.extractId(jwt);
//
//        seriesViewCountService.addSeriesToViewCount(id, seriesId);
//        return ResponseEntity.ok("Series added to view count");
//    }

    @PostMapping("/increment-view-count")
    public ResponseEntity<Void> incrementViewCount(@RequestParam Integer accountId, @RequestParam Integer seriesId, @RequestParam Integer episodeId) {
        seriesViewCountService.incrementViewCount(accountId, seriesId, episodeId);
        return ResponseEntity.ok().build();
    }
}
