package com.example.ReviewService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReviewResponse {
    private Long id;
    private Long movieId;
    private String userEmail;
    private String comment;
    private int rating;
    private LocalDateTime createdAt;
}
