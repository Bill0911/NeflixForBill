package com.example.netflix.controller;

import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.SeriesViewCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/series")
public class SeriesViewCountController
{
    private final SeriesViewCountService seriesViewCountService;
    private final JwtUtil jwtUtil;

    public SeriesViewCountController(SeriesViewCountService seriesViewCountService, JwtUtil jwtUtil)
    {
        this.seriesViewCountService = seriesViewCountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/increment-view-count")
    public ResponseEntity<String> incrementViewCount(@RequestParam Integer accountId, @RequestParam Integer seriesId)
    {
        seriesViewCountService.incrementViewCount(accountId, seriesId);
        return ResponseEntity.ok("View count updated");
    }

    @PostMapping("/add-to-viewcount")
    public ResponseEntity<String> addToViewCount(@RequestParam Integer seriesId, @RequestHeader("Authorization") String token)
    {
        String jwt = token.substring(7);
        int id = jwtUtil.extractId(jwt);

        seriesViewCountService.addSeriesToViewCount(id, seriesId);
        return ResponseEntity.ok("Movie added to view count");
    }
}
