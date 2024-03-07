package com.cine.verse.mappers;

import com.cine.verse.Dto.request.ReviewRequest;
import com.cine.verse.Dto.response.ReviewResponse;
import com.cine.verse.domain.Review;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public interface ReviewMapper {
    Review reviewRequestToReview(ReviewRequest reviewRequest);

    ReviewResponse reviewToReviewResponse(Review review);
}
