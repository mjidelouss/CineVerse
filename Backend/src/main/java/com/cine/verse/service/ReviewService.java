package com.cine.verse.service;

import com.cine.verse.Dto.request.ReviewRequest;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    Page<Review> getReviews(Pageable pageable);

    List<Review> getRecentReviews(Long movieId);

    Review getReviewById(Long id);

    List<Review> getUserRecentReviews(Long userId);

    List<Movie> getMoviesLiked(Long userId);

    List<Object[]> getReviewdMovies(Long userId);

    Review getReviewByMovieAndUser(Long movieId, Long userId);
    Review addReview(ReviewRequest reviewRequest);

    Review updateReview(Review review);

    long getTotalReviews();
    List<Review> getPopularReviews();

    void deleteReview(Long id);

    Boolean rateMovie(Long movieId, Long userId, Integer rate);

    Boolean watchListMovie(Long movieId, Long userId, Boolean watchlist);

    Boolean likeMovie(Long movieId, Long userId, Boolean like);

    List<Review> getUserReviews(Long userId);

    Long getWatchedMoviesCount(Long userId);
    Long getLikedMoviesCount(Long userId);
    Long getCountOfUsersLikedMovie(Long movieId);
    Long getCountOfUsersWatchedMovie(Long movieId);
}
