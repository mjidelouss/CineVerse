package com.cine.verse.repository;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByMovieIdAndAppUserId(Long movieId, Long userId);
}
