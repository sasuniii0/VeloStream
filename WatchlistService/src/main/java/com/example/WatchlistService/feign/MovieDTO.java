package com.example.WatchlistService.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieDTO {
    private Long id;
    private String title;
    private String genre;
    private String posterUrl;
    private double rating;
}
