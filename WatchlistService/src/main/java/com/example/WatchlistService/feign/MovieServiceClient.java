package com.example.WatchlistService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "movie-service")
public interface MovieServiceClient {
    @GetMapping("/api/movies/{id}")
    MovieDTO getMovieById(@PathVariable("id") Long id);
}
