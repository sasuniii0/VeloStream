package com.example.ReviewService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "movie-service")
public interface MovieServiceClient {

    // calls PUT /api/movies/{id}/rating?rating=4.5 in movie-service
    @PutMapping("/api/movies/{id}/rating")
    void updateMovieRating(
            @PathVariable("id") Long movieId,
            @RequestParam("rating") double rating
    );
}
