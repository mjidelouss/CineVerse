package com.cine.verse.repository;

import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId AND r.appUser.id = :userId ORDER BY r.timestamp DESC LIMIT 1")
    Optional<Review> findByMovieIdAppUserId(@Param("movieId") Long movieId, @Param("userId") Long userId);
    List<Review> findByMovieIdAndContentIsNotNull(Long movieId);

    List<Review> findByAppUserIdAndContentIsNotNull(Long userId);

    List<Review> findTop3ByAppUserIdAndContentIsNotNullOrderByTimestampDesc(Long userId);
    @Query("SELECT r.movie FROM Review r WHERE r.appUser.id = :userId AND r.liked = true")
    List<Movie> findMoviesByAppUserIdAndLikedTrue(@Param("userId") Long userId);

    @Query("SELECT DISTINCT r.movie, r.rating FROM Review r WHERE r.appUser.id = :userId")
    List<Object[]> findMoviesAndRatingsByAppUserId(@Param("userId") Long userId);
}
