package com.cine.verse.controller;

import com.cine.verse.Dto.request.RateRequest;
import com.cine.verse.Dto.request.ReviewRequest;
import com.cine.verse.Dto.response.ReviewResponse;
import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.mappers.ReviewMapper;
import com.cine.verse.domain.Review;
import com.cine.verse.repository.AppUserRepository;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.MovieService;
import com.cine.verse.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final AppUserRepository appUserRepository;
    private final MovieService movieService;

    @GetMapping("/all")
    public ResponseEntity getReviews() {
        List<Review> reviews = reviewService.getReviews();
        if (reviews.isEmpty()) {
            return ResponseMessage.notFound("Reviews Not Found");
        } else {
            return ResponseMessage.ok("Success", reviews.stream().map(this::reviewToReviewResponse).collect(Collectors.toList()));
        }
    }

    @GetMapping("/recent/{movieId}")
    public ResponseEntity getRecentReviews(@PathVariable Long movieId) {
        List<Review> reviews = reviewService.getRecentReviews(movieId);
        if (reviews.isEmpty()) {
            return ResponseMessage.notFound("Recent Reviews Not Found");
        } else {
            return ResponseMessage.ok("Successfully retrieved Recent Reviews", reviews);
        }
    }

    @GetMapping("/{movieId}/{userId}")
    public ResponseEntity getReviewByMovieAndUser(@PathVariable Long movieId, @PathVariable Long userId) {
        Review review = reviewService.getReviewByMovieAndUser(movieId, userId);
        if (review == null) {
            return ResponseMessage.notFound("Review Not Found");
        } else {
            return ResponseMessage.ok("Success", review);
        }
    }

    @GetMapping("/reviewed-movies/{userId}")
    public ResponseEntity getReviewedMovies(@PathVariable Long userId) {
        List<Object[]> movies = reviewService.getReviewdMovies(userId);
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("Reviewed Movies Not Found");
        } else {
            return ResponseMessage.ok("Successfully Obtiend Reviewed Movies", movies);
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

    @GetMapping("/diary/{userId}")
    public ResponseEntity getUserReviews(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getUserReviews(userId);
        if (reviews.isEmpty()) {
            return ResponseMessage.notFound("User Reviews Not Found");
        } else {
            return ResponseMessage.ok("Success", reviews);
        }
    }

    @GetMapping("/liked/{userId}")
    public ResponseEntity getLikedMovies(@PathVariable Long userId) {
        List<Movie> movies = reviewService.getMoviesLiked(userId);
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("User Liked Movies Not Found");
        } else {
            return ResponseMessage.ok("Success", movies);
        }
    }

    @PostMapping("")
    public ResponseEntity addReview(@RequestBody @Valid ReviewRequest reviewRequest) {
        Review review1;
        // Fetch the existing review
        Review review = reviewService.getReviewByMovieAndUser(reviewRequest.getMovieId(), reviewRequest.getUserId());

        if (review == null) {
            // If the review doesn't exist, create a new one
            review = new Review();
            review.setAppUser(appUserRepository.findById(reviewRequest.getUserId()).orElse(null));
            review.setMovie(movieService.getMovieById(reviewRequest.getMovieId()));
            review.setContent(reviewRequest.getContent());
            review.setTimestamp(LocalDate.now());
            review1 = reviewService.addReview(review);
        } else {
            if (review.getContent() == null) {
                // If the existing review has no content, update it
                review.setContent(reviewRequest.getContent());
                review.setTimestamp(LocalDate.now());
                review1 = reviewService.addReview(review);
            } else {
                // If the existing review has content, create a new one based on it
                Review review2 = new Review();
                review2.setRating(review.getRating());
                review2.setWatched(review.getWatched());
                review2.setLiked(review.getLiked());
                review2.setAppUser(review.getAppUser());
                review2.setMovie(review.getMovie());
                review2.setContent(reviewRequest.getContent());
                review2.setTimestamp(LocalDate.now());
                review1 = reviewService.addReview(review2);
            }
        }

        if (review1 == null) {
            return ResponseMessage.badRequest("Failed To Create Review");
        } else {
            return ResponseMessage.created("Review Created Successfully", review1);
        }
    }


    @PostMapping(value = "rate/{num}")
    public ResponseEntity addRating(@RequestBody @Valid RateRequest rateRequest, @PathVariable Integer num) {
        ReviewResponse response = reviewService.rateMovie(rateRequest.getMovieId(), rateRequest.getUserId(), num);
        if(response == null) {
            return ResponseMessage.badRequest("Failed To Add Rating");
        } else {
            return ResponseMessage.created("Rating Created Successfully", response);
        }
    }

    @PostMapping(value = "like/{bool}")
    public ResponseEntity addMovieLike(@RequestBody @Valid RateRequest rateRequest, @PathVariable Boolean bool) {
        Boolean response = reviewService.likeMovie(rateRequest.getMovieId(), rateRequest.getUserId(), bool);
        if(response == null) {
            return ResponseMessage.badRequest("Failed To Add Movie Like");
        } else {
            return ResponseMessage.created("Like Movie Created Successfully", response);
        }
    }

    @PostMapping(value = "watchlist/{bool}")
    public ResponseEntity addMovieWatchList(@RequestBody @Valid RateRequest rateRequest, @PathVariable Boolean bool) {
        Boolean response = reviewService.watchListMovie(rateRequest.getMovieId(), rateRequest.getUserId(), bool);
        if(response == null) {
            return ResponseMessage.badRequest("Failed To Add Movie WatchList");
        } else {
            return ResponseMessage.created("Added Movie to WatchList Successfully", response);
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

    private ReviewResponse reviewToReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setFirstname(review.getAppUser().getFirstname());
        reviewResponse.setLastname(review.getAppUser().getLastname());
        reviewResponse.setImage(review.getAppUser().getImage());

        reviewResponse.setContent(review.getContent());
        reviewResponse.setRating(review.getRating());
        reviewResponse.setTimestamp(review.getTimestamp());
        reviewResponse.setLikes(review.getLikes());
        return reviewResponse;
    }
}
