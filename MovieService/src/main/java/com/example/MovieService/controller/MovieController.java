package com.example.MovieService.controller;

import com.example.MovieService.dto.MovieRequest;
import com.example.MovieService.dto.MovieResponse;
import com.example.MovieService.enums.Genre;
import com.example.MovieService.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    // GET all — public
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAll() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // GET one — public
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    // GET search + filter — public
    // Usage: /api/movies/search?title=avengers&genre=ACTION
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Genre genre) {
        return ResponseEntity.ok(movieService.searchAndFilter(title, genre));
    }

    // POST add — ADMIN only
    @PostMapping
    public ResponseEntity<MovieResponse> add(@RequestBody MovieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movieService.addMovie(request));
    }

    // PUT update — ADMIN only
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(
            @PathVariable Long id,
            @RequestBody MovieRequest request) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    // DELETE — ADMIN only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    // PUT update rating — called internally by Review Service
    @PutMapping("/{id}/rating")
    public ResponseEntity<Void> updateRating(
            @PathVariable Long id,
            @RequestParam double rating) {
        movieService.updateRating(id, rating);
        return ResponseEntity.ok().build();
    }
}
