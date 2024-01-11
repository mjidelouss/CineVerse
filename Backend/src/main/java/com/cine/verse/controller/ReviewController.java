package com.cine.verse.controller;

import com.cine.verse.domain.Like;
import com.cine.verse.domain.Review;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.LikeService;
import com.cine.verse.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity getReviews() {
        List<Review> reviews = reviewService.getReviews();
        if (reviews.isEmpty()) {
            return ResponseMessage.notFound("Reviews Not Found");
        } else {
            return ResponseMessage.ok("Success", reviews);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            return ResponseMessage.notFound("Review Not Found");
        } else {
            return ResponseMessage.ok("Success", review);
        }
    }

    @PostMapping("")
    public ResponseEntity addReview(@RequestBody @Valid ReviewRequest reviewRequest) {
        Review review = ReviewMapper.mapMemberRequestToMember(reviewRequest);
        Review review1 = reviewService.addReview(review);
        if(review1 == null) {
            return ResponseMessage.badRequest("Failed To Create Review");
        } else {
            return ResponseMessage.created("Review Created Successfully", review1);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateReview(@RequestBody @Valid ReviewRequest reviewRequest, @PathVariable Long id) {
        Review review = ReviewMapper.mapMemberRequestToMember(reviewRequest);
        Review review1 = reviewService.updateReview(review, id);
        if (review1 == null) {
            return ResponseMessage.badRequest("Review Not Updated");
        } else {
            return ResponseMessage.created("Review Updated Successfully", review1);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            return ResponseMessage.notFound("Review Not Found");
        } else {
            reviewService.deleteReview(id);
            return ResponseMessage.ok("Review Deleted Successfully", review);
        }
    }
}
