package com.example.netflix.controller;

import com.example.netflix.entity.SeriesViewCount;
import com.example.netflix.security.JwtUtil;
import com.example.netflix.service.SeriesService;
import com.example.netflix.service.SeriesViewCountService;
import com.example.netflix.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/series-view-count")
public class SeriesViewCountController {

    private final SeriesViewCountService seriesViewCountService;
    private final JwtUtil jwtUtil;

    private final SeriesService seriesService;

    private final UserService userService;

    public SeriesViewCountController(SeriesViewCountService seriesViewCountService, JwtUtil jwtUtil, SeriesService seriesService, UserService userService) {
        this.seriesViewCountService = seriesViewCountService;
        this.jwtUtil = jwtUtil;
        this.seriesService = seriesService;
        this.userService = userService;
    }

//    @PostMapping("/add-to-view-count")
//    public ResponseEntity<String> addToViewCount(@RequestParam Integer seriesId, @RequestHeader("Authorization") String token) {
//        String jwt = token.substring(7);
//        int id = jwtUtil.extractId(jwt);
//
//        seriesViewCountService.addSeriesToViewCount(id, seriesId);
//        return ResponseEntity.ok("Series added to view count");
//    }

    @PostMapping("/{accountId}/{seriesId}")
    public ResponseEntity<String> addSeriesViewCount(@PathVariable Integer accountId,  @PathVariable Integer seriesId) {
        try {
            userService.getUserById(accountId);
            seriesService.getSeriesById(seriesId);
            seriesViewCountService.addSeriesViewCount(accountId, seriesId);
            return ResponseEntity.ok("Series - User relation has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{accountId}/{seriesId}")
    public ResponseEntity<Object> getSeriesViewCount(@PathVariable Integer accountId, @PathVariable Integer seriesId) {
        return ResponseEntity.ok(seriesViewCountService.getSeriesViewCount(accountId, seriesId));
    }

    @GetMapping("/many")
    public ResponseEntity<Object> getManySeriesViewCounts() {
        return ResponseEntity.ok(seriesViewCountService.getManySeriesViewCounts());
    }

    @DeleteMapping("/{accountId}/{seriesId}")
    public ResponseEntity<Object> deleteSeriesViewCount(@PathVariable Integer accountId, @PathVariable Integer seriesId) {
        try {
            seriesViewCountService.deleteSeriesViewCount(accountId, seriesId);
            return ResponseEntity.ok("Series - User relation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PatchMapping()
    public ResponseEntity<Object> patchSeriesViewCount(@RequestBody SeriesViewCount seriesViewCount) {
        try {
            System.out.println("CHECKPOINT - 1");
            seriesViewCountService.patchSeriesViewCount(seriesViewCount);
            System.out.println("CHECKPOINT - 2");
            return ResponseEntity.ok(seriesViewCount);
        } catch (Exception e) {
            System.out.println("CHECKPOINT - error1");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<Object> putSeriesViewCount(@RequestBody SeriesViewCount seriesViewCount) {
        try {
            seriesViewCountService.updateSeriesViewCount(seriesViewCount);
            return ResponseEntity.ok(seriesViewCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());
        }
    }
}
