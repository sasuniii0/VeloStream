package com.example.ReviewService.service;

import com.example.ReviewService.dto.ReviewRequest;
import com.example.ReviewService.dto.ReviewResponse;
import com.example.ReviewService.entity.Review;
import com.example.ReviewService.feign.MovieServiceClient;
import com.example.ReviewService.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieServiceClient movieServiceClient;

    // ── POST a review ────────────────────────────────────
    public ReviewResponse addReview(ReviewRequest request, String userEmail) {

        // prevent duplicate review from same user on same movie
        if (reviewRepository.existsByMovieIdAndUserEmail(
                request.getMovieId(), userEmail)) {
            throw new RuntimeException(
                    "You have already reviewed this movie");
        }

        // save the review
        Review review = Review.builder()
                .movieId(request.getMovieId())
                .userEmail(userEmail)
                .comment(request.getComment())
                .rating(request.getRating())
                .build();

        Review saved = reviewRepository.save(review);

        // recalculate average rating and push to movie-service via Feign
        updateMovieAverageRating(request.getMovieId());

        return toResponse(saved);
    }

    // ── GET all reviews for a movie ──────────────────────
    public List<ReviewResponse> getReviewsByMovie(Long movieId) {
        return reviewRepository.findByMovieId(movieId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── GET average rating for a movie ───────────────────
    public double getAverageRating(Long movieId) {
        Double avg = reviewRepository.findAverageRatingByMovieId(movieId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    // ── recalculate and push average to movie-service ────
    private void updateMovieAverageRating(Long movieId) {
        double avg = getAverageRating(movieId);
        try {
            movieServiceClient.updateMovieRating(movieId, avg);
        } catch (Exception e) {
            // log but don't fail the review save if movie-service is down
            System.err.println(
                    "Warning: could not update movie rating — " + e.getMessage());
        }
    }

    // ── MAPPER ───────────────────────────────────────────
    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .movieId(review.getMovieId())
                .userEmail(review.getUserEmail())
                .comment(review.getComment())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
