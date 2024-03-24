package com.cine.verse.controller;

import com.cine.verse.Dto.request.RateRequest;
import com.cine.verse.Dto.request.ReviewRequest;
import com.cine.verse.Dto.response.*;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;
import com.cine.verse.mappers.ReviewMapper;
import com.cine.verse.mappers.UserMapper;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity getReviews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<Review> reviewsPage = reviewService.getReviews(PageRequest.of(page, size));

        if (reviewsPage.isEmpty()) {
            return ResponseMessage.notFound("Reviews Not Found");
        } else {
            Page<ReviewsResponse> reviewsResponses = reviewsPage.map(ReviewMapper::convertReviewToReviewsResponse);
            return ResponseMessage.ok("Success", reviewsResponses.getContent());
        }
    }

    @GetMapping("/recent/{movieId}")
    public ResponseEntity getRecentReviews(@PathVariable Long movieId) {
        List<Review> reviews = reviewService.getRecentReviews(movieId);
        List<RecentReviewResponse> recentReviews = reviews.stream()
                .map(ReviewMapper::convertReviewToRecentReview)
                .collect(Collectors.toList());
        if (recentReviews.isEmpty()) {
            return ResponseMessage.notFound("Recent Reviews Not Found");
        } else {
            return ResponseMessage.ok("Successfully retrieved Recent Reviews", recentReviews);
        }
    }

    @GetMapping("/user-review/{userId}")
    public ResponseEntity getUserRecentReviews(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getUserRecentReviews(userId);
        List<RecentUserReviewResponse> recentUserReviews = reviews.stream()
                .map(ReviewMapper::convertReviewToRecentUserReview)
                .collect(Collectors.toList());
        if (recentUserReviews.isEmpty()) {
            return ResponseMessage.notFound("User's Recent Reviews Not Found");
        } else {
            return ResponseMessage.ok("Successfully retrieved User's Recent Reviews", recentUserReviews);
        }
    }
    
    @GetMapping("/{movieId}/{userId}")
    public ResponseEntity getReviewByMovieAndUser(@PathVariable Long movieId, @PathVariable Long userId) {
        Review review = reviewService.getReviewByMovieAndUser(movieId, userId);
        if (review == null) {
            return ResponseMessage.notFound("Review Not Found");
        } else {
            ReviewResponse reviewResponse = ReviewMapper.convertReviewToReviewResponse(review);
            return ResponseMessage.ok("Success", reviewResponse);
        }
    }

    @GetMapping("/reviewed-movies/{userId}")
    public ResponseEntity getReviewedMovies(@PathVariable Long userId) {
        List<Object[]> movies = reviewService.getReviewdMovies(userId);
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("Reviewed Movies Not Found");
        } else {
            List<ReviewedMovieResponse> reviewedMovieResponses = movies.stream()
                    .map(ReviewMapper::convertObjectArrayToReviewedMovieResponse)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Successfully Obtiend Reviewed Movies", reviewedMovieResponses);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            return ResponseMessage.notFound("Review Not Found");
        } else {
            ReviewResponse reviewResponse = ReviewMapper.convertReviewToReviewResponse(review);
            return ResponseMessage.ok("Success", reviewResponse);
        }
    }

    @GetMapping("/diary/{userId}")
    public ResponseEntity getUserReviews(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getUserReviews(userId);
        if (reviews.isEmpty()) {
            return ResponseMessage.notFound("User Reviews Not Found");
        } else {
            List<UserReviewResponse> userReviewResponses = reviews.stream()
                    .map(UserMapper::convertReviewToUserReview)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", userReviewResponses);
        }
    }

    @GetMapping("/popular")
    public ResponseEntity getPopularReviews() {
        List<Review> reviews = reviewService.getPopularReviews();
        if (reviews.isEmpty()) {
            return ResponseMessage.notFound("Popular Reviews Not Found");
        } else {
            List<PopularReviewResponse> popularReviewResponses = reviews.stream()
                    .map(ReviewMapper::convertReviewToPopularReview)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", popularReviewResponses);
        }
    }

    @GetMapping("/watch-count/{userId}")
    public ResponseEntity getUsersMoviesWatchedCount(@PathVariable Long userId) {
        Long count = reviewService.getWatchedMoviesCount(userId);
        if (count == null) {
            return ResponseMessage.notFound("Movies Watched Count Not Found");
        } else {
            return ResponseMessage.ok("Success", count);
        }
    }

    @GetMapping("/like-count/{userId}")
    public ResponseEntity getUsersMoviesLikedCount(@PathVariable Long userId) {
        Long count = reviewService.getLikedMoviesCount(userId);
        if (count == null) {
            return ResponseMessage.notFound("Movies Watched Count Not Found");
        } else {
            return ResponseMessage.ok("Success", count);
        }
    }

    @GetMapping("/liked/{userId}")
    public ResponseEntity getLikedMovies(@PathVariable Long userId) {
        List<Movie> movies = reviewService.getMoviesLiked(userId);
        if (movies.isEmpty()) {
            return ResponseMessage.notFound("User Liked Movies Not Found");
        } else {
            List<UserLikedMovieResponse> likedMovieResponses = movies.stream()
                    .map(UserMapper::convertMovieToUserLikedMovie)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", likedMovieResponses);
        }
    }

    @GetMapping("/count")
    public ResponseEntity getTotalReviews() {
        long totalReviews = reviewService.getTotalReviews();
        return ResponseMessage.ok("Success", totalReviews);
    }

    @GetMapping("/total-watched/{movieId}")
    public ResponseEntity getTotalUsersWatchedMovie(@PathVariable Long movieId) {
        long total = reviewService.getCountOfUsersWatchedMovie(movieId);
        return ResponseMessage.ok("Success", total);
    }

    @GetMapping("/total-liked/{movieId}")
    public ResponseEntity getTotalUsersLikedMovie(@PathVariable Long movieId) {
        long total = reviewService.getCountOfUsersLikedMovie(movieId);
        return ResponseMessage.ok("Success", total);
    }


    @GetMapping("/filterLikedMoviesByGenre/{userId}")
    public ResponseEntity getLikedMoviesByGenre(@RequestParam(name = "genre", required = false) String genreName, @PathVariable Long userId) {
        List<Movie> allMovies = reviewService.getMoviesLiked(userId);
        List<UserLikedMovieResponse> likedMovieResponses = allMovies.stream()
                .map(UserMapper::convertMovieToUserLikedMovie)
                .collect(Collectors.toList());
        if (genreName == null || genreName.isEmpty()) {
            return ResponseMessage.ok("No Genre Specified", likedMovieResponses);
        }
        List <Movie> filterdLikedMovies = allMovies.stream()
                .filter(movie -> movie.getGenres().stream()
                        .anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName))).collect(Collectors.toList());
        if (filterdLikedMovies.isEmpty()) {
            return ResponseMessage.notFound("User Liked Movies Not Found");
        } else {
            List<UserLikedMovieResponse> userLikedMovieResponses = filterdLikedMovies.stream()
                    .map(UserMapper::convertMovieToUserLikedMovie)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", userLikedMovieResponses);
        }
    }

    @GetMapping("/filterLikedMoviesByDecade/{userId}")
    public ResponseEntity getLikedMoviesByDecade(@RequestParam("decade") String decade, @PathVariable Long userId) {
        List<Movie> allMovies = reviewService.getMoviesLiked(userId);
        List<UserLikedMovieResponse> likedMovieResponses = allMovies.stream()
                .map(UserMapper::convertMovieToUserLikedMovie)
                .collect(Collectors.toList());
        if (decade == null || decade.isEmpty()) {
            return ResponseMessage.ok("No Decade Specified", likedMovieResponses);
        }

        List<Movie> filterdMovies =  allMovies.stream().filter(movie -> isMovieInDecade(movie, decade))
                .collect(Collectors.toList());
        if (filterdMovies.isEmpty()) {
            return ResponseMessage.notFound("User Liked Movies Not Found");
        } else {
            List<UserLikedMovieResponse> userLikedMovieResponses = filterdMovies.stream()
                    .map(UserMapper::convertMovieToUserLikedMovie)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", userLikedMovieResponses);
        }
    }

    @GetMapping("/filterDiaryMoviesByGenre/{userId}")
    public ResponseEntity getDiaryMoviesByGenre(@RequestParam(name = "genre", required = false) String genreName, @PathVariable Long userId) {
        List<Review> allReviews = reviewService.getUserReviews(userId);
        List<UserReviewResponse> userReviewResponses = allReviews.stream()
                .map(UserMapper::convertReviewToUserReview).collect(Collectors.toList());
        if (genreName == null || genreName.isEmpty()) {
            return ResponseMessage.ok("No Genre Specified", userReviewResponses);
        }
        List<Review> filteredReviews = allReviews.stream()
                .filter(review -> {
                    return review.getMovie().getGenres().stream()
                            .anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName));
                }).collect(Collectors.toList());
        if (filteredReviews.isEmpty()) {
            return ResponseMessage.notFound("Filtered Diary Movies Not Found");
        } else {
            List<UserReviewResponse> diaryResponses = filteredReviews.stream()
                    .map(UserMapper::convertReviewToUserReview)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", diaryResponses);
        }
    }

    @GetMapping("/filterDiaryMoviesByDecade/{userId}")
    public ResponseEntity getDiaryMoviesByDecade(@RequestParam("decade") String decade, @PathVariable Long userId) {
        List<Review> allReviews = reviewService.getUserReviews(userId);
        List<UserReviewResponse> userReviewResponses = allReviews.stream()
                .map(UserMapper::convertReviewToUserReview).collect(Collectors.toList());
        if (decade == null || decade.isEmpty()) {
            return ResponseMessage.ok("No Genre Specified", userReviewResponses);
        }
        List<Review> filteredReviews = allReviews.stream().filter(review -> isMovieInDecade(review.getMovie(), decade))
                .collect(Collectors.toList());
        if (filteredReviews.isEmpty()) {
            return ResponseMessage.notFound("Filtered Diary Movies Not Found");
        } else {
            List<UserReviewResponse> diaryResponses = filteredReviews.stream()
                    .map(UserMapper::convertReviewToUserReview)
                    .collect(Collectors.toList());
            return ResponseMessage.ok("Success", diaryResponses);
        }
    }

    @PostMapping("")
    public ResponseEntity addReview(@RequestBody @Valid ReviewRequest reviewRequest) {
        Review review1 = reviewService.addReview(reviewRequest);
        if (review1 == null) {
            return ResponseMessage.badRequest("Failed To Create Review");
        } else {
            RecentReviewResponse reviewResponse = ReviewMapper.convertReviewToRecentReview(review1);
            return ResponseMessage.created("Review Created Successfully", reviewResponse);
        }
    }


    @PostMapping(value = "rate/{num}")
    public ResponseEntity addRating(@RequestBody @Valid RateRequest rateRequest, @PathVariable Integer num) {
        Boolean response = reviewService.rateMovie(rateRequest.getMovieId(), rateRequest.getUserId(), num);
        if(!response) {
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
        Review review = reviewService.getReviewById(id);
        review.setContent(reviewRequest.getContent());
        Review review1 = reviewService.updateReview(review);
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
    private boolean isMovieInDecade(Movie movie, String decade) {
        int year = movie.getYear();
        switch (decade.toLowerCase()) {
            case "1860s":
                return year >= 1860 && year <= 1869;
            case "1870s":
                return year >= 1870 && year <= 1879;
            case "1880s":
                return year >= 1880 && year <= 1889;
            case "1890s":
                return year >= 1890 && year <= 1899;
            case "1900s":
                return year >= 1900 && year <= 1909;
            case "1910s":
                return year >= 1910 && year <= 1919;
            case "1920s":
                return year >= 1920 && year <= 1929;
            case "1930s":
                return year >= 1930 && year <= 1939;
            case "1940s":
                return year >= 1940 && year <= 1949;
            case "1950s":
                return year >= 1950 && year <= 1959;
            case "1960s":
                return year >= 1960 && year <= 1969;
            case "1970s":
                return year >= 1970 && year <= 1979;
            case "1980s":
                return year >= 1980 && year <= 1989;
            case "1990s":
                return year >= 1990 && year <= 1999;
            case "2000s":
                return year >= 2000 && year <= 2009;
            case "2010s":
                return year >= 2010 && year <= 2019;
            case "2020s":
                return year >= 2020 && year <= 2024; // Up to 2024
            default:
                return false;
        }
    }
}
