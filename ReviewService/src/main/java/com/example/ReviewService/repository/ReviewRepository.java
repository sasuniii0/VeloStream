package com.example.ReviewService.repository;

import com.example.ReviewService.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // get all reviews for a specific movie
    List<Review> findByMovieId(Long movieId);

    // check if user already reviewed this movie
    boolean existsByMovieIdAndUserEmail(Long movieId, String userEmail);

    // get average rating for a movie
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movieId = :movieId")
    Double findAverageRatingByMovieId(@Param("movieId") Long movieId);

    // count reviews for a movie
    long countByMovieId(Long movieId);
}
