package com.example.WatchlistService.controller;

import com.example.WatchlistService.dto.WatchlistResponse;
import com.example.WatchlistService.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {
    private final WatchlistService watchlistService;

    // ADD movie to watchlist
    @PostMapping("/{movieId}")
    public ResponseEntity<WatchlistResponse> add(
            @PathVariable Long movieId,
            Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(watchlistService.addToWatchlist(movieId, userEmail));
    }

    // REMOVE movie from watchlist
    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long movieId,
            Authentication authentication) {
        String userEmail = authentication.getName();
        watchlistService.removeFromWatchlist(movieId, userEmail);
        return ResponseEntity.noContent().build();
    }

    // GET my full watchlist
    @GetMapping
    public ResponseEntity<List<WatchlistResponse>> getMyWatchlist(
            Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(watchlistService.getMyWatchlist(userEmail));
    }

    // CHECK if specific movie is in watchlist
    @GetMapping("/check/{movieId}")
    public ResponseEntity<Boolean> check(
            @PathVariable Long movieId,
            Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(
                watchlistService.isInWatchlist(movieId, userEmail));
    }
}
