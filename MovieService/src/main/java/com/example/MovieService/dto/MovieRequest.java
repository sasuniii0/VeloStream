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
public class MovieRequest {
    private String title;
    private String description;
    private String director;
    private int releaseYear;
    private Genre genre;
    private String posterUrl;
}
