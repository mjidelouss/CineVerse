package com.cine.verse.service;

import com.cine.verse.Dto.response.ReviewResponse;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews();

    List<Review> getRecentReviews(Long movieId);

    Review getReviewById(Long id);

    List<Movie> getMoviesLiked(Long userId);

    List<Object[]> getReviewdMovies(Long userId);

    Review getReviewByMovieAndUser(Long movieId, Long userId);
    Review addReview(Review review);

    Review updateReview(Review review, Long id);

    void deleteReview(Long id);

    ReviewResponse rateMovie(Long movieId, Long userId, Integer rate);

    Boolean watchListMovie(Long movieId, Long userId, Boolean watchlist);

    Boolean likeMovie(Long movieId, Long userId, Boolean like);

    List<Review> getUserReviews(Long userId);
}
