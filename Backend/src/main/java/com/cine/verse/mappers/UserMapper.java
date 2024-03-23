package com.cine.verse.mappers;

import com.cine.verse.Dto.response.UserLikedMovieResponse;
import com.cine.verse.Dto.response.UserResponse;
import com.cine.verse.Dto.response.UserReviewResponse;
import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
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

    public static UserLikedMovieResponse convertMovieToUserLikedMovie(Movie movie) {
        return UserLikedMovieResponse.builder()
                .movieId(movie.getId())
                .year(movie.getYear())
                .title(movie.getTitle())
                .image(movie.getImage())
                .build();
    }

    public static UserReviewResponse convertReviewToUserReview(Review review) {
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
