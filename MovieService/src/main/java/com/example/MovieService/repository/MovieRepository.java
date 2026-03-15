package com.example.MovieService.repository;

import com.example.MovieService.entity.Movie;
import com.example.MovieService.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // search by title (case insensitive)
    List<Movie> findByTitleContainingIgnoreCase(String title);

    // filter by genre
    List<Movie> findByGenre(Genre genre);

    // search by title AND genre
    List<Movie> findByTitleContainingIgnoreCaseAndGenre(String title, Genre genre);
}
