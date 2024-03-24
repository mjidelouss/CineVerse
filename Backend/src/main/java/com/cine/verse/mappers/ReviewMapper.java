package com.cine.verse.mappers;

import com.cine.verse.Dto.response.*;
import com.cine.verse.domain.Movie;
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
                 .userImage(review.getAppUser().getImage())
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

    public static ReviewedMovieResponse convertObjectArrayToReviewedMovieResponse(Object[] objectArray) {
        Movie movie = (Movie) objectArray[0];
        Integer rating = (Integer) objectArray[1];

        return ReviewedMovieResponse.builder()
                .movieId(movie.getId())
                .rating(rating)
                .movieImage(movie.getImage())
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
