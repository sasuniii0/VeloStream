package com.example.WatchlistService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WatchlistResponse {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private String movieGenre;
    private String posterUrl;
    private LocalDateTime addedAt;
}
