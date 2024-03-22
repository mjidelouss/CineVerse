package com.cine.verse.mappers;

import com.cine.verse.Dto.response.UserWatchListResponse;
import com.cine.verse.domain.Review;

public class WatchListMapper {
    public static UserWatchListResponse convertReviewToUserWatchList(Review review) {
        return UserWatchListResponse.builder()
                .movieId(review.getMovie().getId())
                .movieImage(review.getMovie().getImage())
                .movieTitle(review.getMovie().getTitle())
                .movieYear(review.getMovie().getYear())
                .build();
    }
}
