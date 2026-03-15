package com.example.MovieService.service;

import com.example.MovieService.dto.MovieRequest;
import com.example.MovieService.dto.MovieResponse;
import com.example.MovieService.entity.Movie;
import com.example.MovieService.enums.Genre;
import com.example.MovieService.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    // ── CREATE ──────────────────────────────────────────
    public MovieResponse addMovie(MovieRequest request) {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .director(request.getDirector())
                .releaseYear(request.getReleaseYear())
                .genre(request.getGenre())
                .posterUrl(request.getPosterUrl())
                .rating(0.0)
                .build();

        return toResponse(movieRepository.save(movie));
    }

    // ── READ ALL ─────────────────────────────────────────
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── READ ONE ─────────────────────────────────────────
    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        return toResponse(movie);
    }

    // ── SEARCH by title ──────────────────────────────────
    public List<MovieResponse> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── FILTER by genre ──────────────────────────────────
    public List<MovieResponse> filterByGenre(Genre genre) {
        return movieRepository.findByGenre(genre)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── SEARCH + FILTER ──────────────────────────────────
    public List<MovieResponse> searchAndFilter(String title, Genre genre) {
        if (title != null && genre != null) {
            return movieRepository
                    .findByTitleContainingIgnoreCaseAndGenre(title, genre)
                    .stream().map(this::toResponse).collect(Collectors.toList());
        } else if (title != null) {
            return searchByTitle(title);
        } else if (genre != null) {
            return filterByGenre(genre);
        }
        return getAllMovies();
    }

    // ── UPDATE ───────────────────────────────────────────
    public MovieResponse updateMovie(Long id, MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setDirector(request.getDirector());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setGenre(request.getGenre());
        movie.setPosterUrl(request.getPosterUrl());

        return toResponse(movieRepository.save(movie));
    }

    // ── DELETE ───────────────────────────────────────────
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    // ── UPDATE RATING (called by Review Service via Feign) ──
    public void updateRating(Long id, double newRating) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setRating(newRating);
        movieRepository.save(movie);
    }

    // ── MAPPER ───────────────────────────────────────────
    private MovieResponse toResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .releaseYear(movie.getReleaseYear())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .posterUrl(movie.getPosterUrl())
                .build();
    }
}
