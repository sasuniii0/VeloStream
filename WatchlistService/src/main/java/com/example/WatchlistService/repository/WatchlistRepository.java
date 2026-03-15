package com.example.WatchlistService.repository;

import com.example.WatchlistService.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    // get all watchlist items for a user
    List<Watchlist> findByUserEmail(String userEmail);

    // check if movie already in user's watchlist
    boolean existsByUserEmailAndMovieId(String userEmail, Long movieId);

    // delete by user and movie
    void deleteByUserEmailAndMovieId(String userEmail, Long movieId);

    // find specific entry
    Optional<Watchlist> findByUserEmailAndMovieId(String userEmail, Long movieId);
}
