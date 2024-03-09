package com.cine.verse.service;

import com.cine.verse.Dto.response.ReviewResponse;
import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews();

    Review getReviewById(Long id);

    Review getReviewByMovieAndUser(Long movieId, Long userId);
    Review addReview(Review review);

    Review updateReview(Review review, Long id);

    void deleteReview(Long id);

    ReviewResponse rateMovie(Long movieId, Long userId, Integer rate);
}
