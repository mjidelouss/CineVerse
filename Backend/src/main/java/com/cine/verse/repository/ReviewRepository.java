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

    @Query("SELECT DISTINCT r.movie, r.rating FROM Review r WHERE r.appUser.id = :userId AND r.rating > 0")
    List<Object[]> findMoviesAndRatingsByAppUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.content IS NOT NULL")
    long countReviewsWithContentNotNull();
    @Query("SELECT r FROM Review r WHERE r.id IN (SELECT MAX(r2.id) FROM Review r2 WHERE r2.content IS NOT NULL AND r2.rating IS NOT NULL GROUP BY r2.appUser) ORDER BY r.id DESC")
    List<Review> findDistinctTop6ByContentIsNotNullAndRatingIsNotNull();

    @Query("SELECT COUNT(DISTINCT r.movie) FROM Review r WHERE r.appUser.id = :userId AND r.watched = true")
    Long countDistinctMoviesWatchedByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(DISTINCT r.movie) FROM Review r WHERE r.appUser.id = :userId AND r.liked = true")
    Long countDistinctMoviesLikedByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(DISTINCT r.appUser.id) FROM Review r WHERE r.movie.id = :movieId AND r.watched = true")
    Long countDistinctUsersWatchedByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT COUNT(DISTINCT r.appUser.id) FROM Review r WHERE r.movie.id = :movieId AND r.liked = true")
    Long countDistinctUsersLikedByMovieId(@Param("movieId") Long movieId);

}
