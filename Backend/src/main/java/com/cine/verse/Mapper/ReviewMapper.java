package com.cine.verse.Mapper;

import com.cine.verse.Dto.request.ReviewRequest;
import com.cine.verse.Dto.response.ReviewResponse;
import com.cine.verse.domain.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review reviewRequestToReview(ReviewRequest reviewRequest);

    ReviewResponse reviewToReviewResponse(Review review);
}
