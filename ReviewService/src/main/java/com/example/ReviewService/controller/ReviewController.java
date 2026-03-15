package com.example.ReviewService.controller;

import com.example.ReviewService.dto.ReviewRequest;
import com.example.ReviewService.dto.ReviewResponse;
import com.example.ReviewService.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    // POST — authenticated users only
    // userEmail is extracted from JWT token — user can't fake it
    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @RequestBody ReviewRequest request,
            Authentication authentication) {

        String userEmail = authentication.getName(); // comes from JWTFilter
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.addReview(request, userEmail));
    }

    // GET all reviews for a movie — public
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getByMovie(
            @PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovie(movieId));
    }

    // GET average rating for a movie — public
    @GetMapping("/movie/{movieId}/average")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getAverageRating(movieId));
    }
}
