package com.example.WatchlistService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WatchlistRequest {
    private Long movieId;
    private String movieTitle;
    private String movieGenre;
    private String posterUrl;
}
