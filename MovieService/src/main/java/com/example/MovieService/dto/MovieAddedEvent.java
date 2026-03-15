package com.example.MovieService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieAddedEvent {
    private Long movieId;
    private String title;
    private String genre;
    private String director;
    private int releaseYear;
}
