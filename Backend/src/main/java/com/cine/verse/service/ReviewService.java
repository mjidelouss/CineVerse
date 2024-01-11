package com.cine.verse.service;

import com.cine.verse.domain.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews();

    Review getReviewById(Long id);

    Review addReview(Review review);

    Review updateReview(Review review, Long id);

    void deleteReview(Long id);
}
