package com.example.WatchlistService.service;

import com.example.WatchlistService.dto.WatchlistResponse;
import com.example.WatchlistService.entity.Watchlist;
import com.example.WatchlistService.feign.MovieDTO;
import com.example.WatchlistService.feign.MovieServiceClient;
import com.example.WatchlistService.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistService {
    private final WatchlistRepository watchlistRepository;
    private final MovieServiceClient movieServiceClient;

    // ── ADD to watchlist ─────────────────────────────────
    public WatchlistResponse addToWatchlist(Long movieId, String userEmail) {

        if (watchlistRepository.existsByUserEmailAndMovieId(userEmail, movieId)) {
            throw new RuntimeException("Movie already in your watchlist");
        }

        // fetch movie details from movie-service via Feign
        MovieDTO movie = movieServiceClient.getMovieById(movieId);

        Watchlist watchlist = Watchlist.builder()
                .userEmail(userEmail)
                .movieId(movieId)
                .movieTitle(movie.getTitle())
                .movieGenre(movie.getGenre())
                .posterUrl(movie.getPosterUrl())
                .build();

        return toResponse(watchlistRepository.save(watchlist));
    }

    // ── REMOVE from watchlist ────────────────────────────
    @Transactional
    public void removeFromWatchlist(Long movieId, String userEmail) {
        if (!watchlistRepository.existsByUserEmailAndMovieId(userEmail, movieId)) {
            throw new RuntimeException("Movie not found in your watchlist");
        }
        watchlistRepository.deleteByUserEmailAndMovieId(userEmail, movieId);
    }

    // ── GET my watchlist ─────────────────────────────────
    public List<WatchlistResponse> getMyWatchlist(String userEmail) {
        return watchlistRepository.findByUserEmail(userEmail)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── CHECK if movie is in watchlist ───────────────────
    public boolean isInWatchlist(Long movieId, String userEmail) {
        return watchlistRepository.existsByUserEmailAndMovieId(userEmail, movieId);
    }

    // ── MAPPER ───────────────────────────────────────────
    private WatchlistResponse toResponse(Watchlist w) {
        return WatchlistResponse.builder()
                .id(w.getId())
                .movieId(w.getMovieId())
                .movieTitle(w.getMovieTitle())
                .movieGenre(w.getMovieGenre())
                .posterUrl(w.getPosterUrl())
                .addedAt(w.getAddedAt())
                .build();
    }
}
