package com.cine.verse.mappers;

import com.cine.verse.Dto.response.UserLikedMovieResponse;
import com.cine.verse.Dto.response.UserResponse;
import com.cine.verse.Dto.response.UserReviewResponse;
import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Review;

public class UserMapper {

    public static UserResponse convertAppUserToUserResponse(AppUser user) {
        return UserResponse.builder()
                .userId(user.getId())
                .bio(user.getBio())
                .firstname(user.getFirstname())
                .image(user.getImage())
                .lastname(user.getLastname())
                .location(user.getLocation())
                .build();
    }

    public static UserLikedMovieResponse convertReviewToUserLikedMovie(Review review) {
        return UserLikedMovieResponse.builder()
                .movieId(review.getMovie().getId())
                .year(review.getMovie().getYear())
                .title(review.getMovie().getTitle())
                .image(review.getMovie().getImage())
                .build();
    }

    public UserReviewResponse convertReviewToUserReview(Review review) {
        return UserReviewResponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .year(review.getMovie().getYear())
                .liked(review.getLiked())
                .movieId(review.getMovie().getId())
                .movieImage(review.getMovie().getImage())
                .movieLanguage(review.getMovie().getLanguage())
                .movieTitle(review.getMovie().getTitle())
                .rating(review.getRating())
                .timestamp(review.getTimestamp())
                .build();
    }
}
