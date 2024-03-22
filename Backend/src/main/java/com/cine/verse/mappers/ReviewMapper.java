package com.cine.verse.mappers;

import com.cine.verse.Dto.response.*;
import com.cine.verse.domain.Review;

public class ReviewMapper {

     public static PopularReviewResponse convertReviewToPopularReview(Review review) {
         return PopularReviewResponse.builder()
                 .rating(review.getRating())
                 .timestamp(review.getTimestamp())
                 .movieYear(review.getMovie().getYear())
                 .movieTitle(review.getMovie().getTitle())
                 .movieImage(review.getMovie().getImage())
                 .content(review.getContent())
                 .movieId(review.getMovie().getId())
                 .userFirstname(review.getAppUser().getFirstname())
                 .userLastname(review.getAppUser().getLastname())
                 .userId(review.getAppUser().getId())
                 .userImage(review.getAppUser().getImage())
                 .build();
    }

    public static RecentReviewResponse convertReviewToRecentReview(Review review) {
         return RecentReviewResponse.builder()
                 .userLastname(review.getAppUser().getLastname())
                 .userFirstname(review.getAppUser().getFirstname())
                 .userId(review.getAppUser().getId())
                 .content(review.getContent())
                 .rating(review.getRating())
                 .timestamp(review.getTimestamp())
                 .build();
    }

    public static RecentUserReviewResponse convertReviewToRecentUserReview(Review review) {
         return RecentUserReviewResponse.builder()
                 .rating(review.getRating())
                 .timestamp(review.getTimestamp())
                 .content(review.getContent())
                 .movieYear(review.getMovie().getYear())
                 .movieId(review.getMovie().getId())
                 .movieImage(review.getMovie().getImage())
                 .movieTitle(review.getMovie().getTitle())
                 .build();
    }

    public static ReviewedMovieResponse convertReviewToReviewedMovie(Review review) {
         return ReviewedMovieResponse.builder()
                 .movieId(review.getMovie().getId())
                 .rating(review.getRating())
                 .movieImage(review.getMovie().getImage())
                 .build();
    }

    public static ReviewResponse convertReviewToReviewResponse(Review review) {
         return ReviewResponse.builder()
                 .watched(review.getWatched())
                 .watchlist(review.getWatchlist())
                 .liked(review.getLiked())
                 .rating(review.getRating())
                 .build();
    }

    public static ReviewsResponse convertReviewToReviewsResponse(Review review) {
         return ReviewsResponse.builder()
                 .reviewId(review.getId())
                 .userFirstname(review.getAppUser().getFirstname())
                 .movieTitle(review.getMovie().getTitle())
                 .rating(review.getRating())
                 .timestamp(review.getTimestamp())
                 .build();
    }

}
