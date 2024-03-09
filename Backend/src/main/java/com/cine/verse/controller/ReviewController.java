package com.cine.verse.controller;

import com.cine.verse.Dto.request.ReviewRequest;
import com.cine.verse.Dto.response.ReviewResponse;
import com.cine.verse.mappers.ReviewMapper;
import com.cine.verse.domain.Review;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

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
        Review review = reviewMapper.reviewRequestToReview(reviewRequest);
        Review review1 = reviewService.addReview(review);
        if(review1 == null) {
            return ResponseMessage.badRequest("Failed To Create Review");
        } else {
            return ResponseMessage.created("Review Created Successfully", review1);
        }
    }

    @PostMapping(value = "rate/{num}")
    public ResponseEntity addRating(@RequestBody @Valid ReviewRequest reviewRequest, @PathVariable Integer num) {
        ReviewResponse response = reviewService.rateMovie(reviewRequest.getMovieId(), reviewRequest.getUserId(), num);
        if(response == null) {
            return ResponseMessage.badRequest("Failed To Add Rating");
        } else {
            return ResponseMessage.created("Rating Created Successfully", response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateReview(@RequestBody @Valid ReviewRequest reviewRequest, @PathVariable Long id) {
        Review review = reviewMapper.reviewRequestToReview(reviewRequest);
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
