package com.example.MovieService.dto;

import com.example.MovieService.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private String director;
    private int releaseYear;
    private Genre genre;
    private double rating;
    private String posterUrl;
}
