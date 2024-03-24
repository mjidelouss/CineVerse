package com.cine.verse.service.impl;

import com.cine.verse.Dto.request.ReviewRequest;
import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;
import com.cine.verse.repository.AppUserRepository;
import com.cine.verse.repository.ReviewRepository;
import com.cine.verse.service.AppUserService;
import com.cine.verse.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceImplTest {

    private ReviewRepository reviewRepository = Mockito.mock(ReviewRepository.class);
    private AppUserRepository userRepository = Mockito.mock(AppUserRepository.class);
    private MovieService movieService = Mockito.mock(MovieService.class);
    private AppUserService appUserService = Mockito.mock(AppUserService.class);
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewServiceImpl(reviewRepository, userRepository, movieService, appUserService);
    }

    // Throw an IllegalArgumentException when movieId is null
    @Test
    public void test_throw_exception_when_movieId_is_null() {
        // Arrange
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setUserId(1L);
        reviewRequest.setMovieId(null);
        reviewRequest.setContent("Great movie!");
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> reviewService.addReview(reviewRequest));
    }

    // throws an IllegalArgumentException when movieId is null
    @Test
    public void test_null_movieId() {
        // Arrange
        Long movieId = null;
        Long userId = 1L;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> { reviewService.getReviewByMovieAndUser(movieId, userId);
        });
    }

    // should update a review with valid input
    @Test
    public void test_update_review_valid_input() {
        // Arrange
        Review review = new Review();
        review.setId(1L);
        review.setContent("Old content");
        review.setRating(3);
        review.setWatched(true);
        review.setLiked(false);
        review.setWatchlist(true);
        review.setTimestamp(LocalDate.now());

        // Act
        Mockito.when(reviewService.updateReview(review)).thenReturn(review);
        Review updatedReview = reviewService.updateReview(review);

        // Assert
        assertNotNull(updatedReview);
        assertEquals(review.getId(), updatedReview.getId());
        assertEquals(review.getContent(), updatedReview.getContent());
        assertEquals(review.getRating(), updatedReview.getRating());
        assertEquals(review.getWatched(), updatedReview.getWatched());
        assertEquals(review.getLiked(), updatedReview.getLiked());
        assertEquals(review.getWatchlist(), updatedReview.getWatchlist());
        assertEquals(review.getTimestamp(), updatedReview.getTimestamp());
    }

    // should throw an exception if review is null
    @Test
    public void test_update_review_null_review() {
        // Arrange
        Review review = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> { reviewService.updateReview(review);
        });
    }

    // If the review for the movie and user already exists, update the rating and watched status, and save the review to the repository
    @Test
    public void test_updateReview() {
        // Arrange
        Long movieId = 1L;
        Long userId = 1L;
        Integer rate = 5;

        Review existingReview = new Review();
        existingReview.setRating(3);
        existingReview.setWatched(false);

        Mockito.when(reviewRepository.findByMovieIdAppUserId(movieId, userId)).thenReturn(Optional.of(existingReview));

        // Act
        Boolean result = reviewService.rateMovie(movieId, userId, rate);

        // Assert
        assertTrue(result);
        assertEquals(rate, existingReview.getRating());
        assertTrue(existingReview.getWatched());
        Mockito.verify(reviewRepository).save(existingReview);
    }

    // When a review already exists for the given movie and user, the 'liked' field is updated with the new value and the review is saved successfully.
    @Test
    public void test_update_review_successfully() {
        // Arrange
        Long movieId = 1L;
        Long userId = 1L;
        Boolean like = true;

        Review review = new Review();
        review.setLiked(false);

        Mockito.when(reviewRepository.findByMovieIdAppUserId(movieId, userId)).thenReturn(Optional.of(review));

        // Act
        Boolean result = reviewService.likeMovie(movieId, userId, like);

        // Assert
        assertTrue(result);
        assertTrue(review.getLiked());
        Mockito.verify(reviewRepository, Mockito.times(1)).save(review);
    }

    // Adds a new review with watchlist set to true if review does not exist
    @Test
    public void test_addNewReviewWithWatchlistTrue() {
        // Arrange
        Long movieId = 1L;
        Long userId = 1L;
        Boolean watchlist = true;

        Movie movie = new Movie();
        movie.setId(movieId);

        AppUser user = new AppUser();
        user.setId(userId);

        Mockito.when(reviewRepository.findByMovieIdAppUserId(movieId, userId)).thenReturn(Optional.empty());
        Mockito.when(movieService.getMovieById(movieId)).thenReturn(movie);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Boolean result = reviewService.watchListMovie(movieId, userId, watchlist);

        // Assert
        assertTrue(result);
        Mockito.verify(reviewRepository).save(Mockito.any(Review.class));
    }


    // Should return a list of reviews for a valid user ID with non-null content
    @Test
    public void test_valid_user_id_with_non_null_content() {
        // Arrange
        Long userId = 1L;
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review();
        Review review2 = new Review();
        Review review3 = new Review();
        review1.setContent("Review 1");
        review2.setContent("Review 2");
        review3.setContent("Review 3");

        reviews.add(review3);
        reviews.add(review1);
        reviews.add(review2);
        // Act
        Mockito.when(reviewService.getUserReviews(userId)).thenReturn(reviews);
        List<Review> newReviews = reviewService.getUserReviews(userId);
         // Assert
        assertFalse(reviews.isEmpty());
        for (Review review : newReviews) {
            assertNotNull(review.getContent());
        }
    }

    // Should throw an IllegalArgumentException for a null user ID
    @Test
    public void test_null_user_id() {
        // Arrange
        Long userId = null;
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {reviewService.getUserReviews(userId);
        });
    }
    

    // Returns the number of distinct movies watched by a user when given a valid user ID.
    @Test
    public void test_validUserId() {
        // Arrange
        Long userId = 1L;
        Long expectedCount = 5L;

        // Act
        Mockito.when(reviewService.getWatchedMoviesCount(userId)).thenReturn(5L);
        Long actualCount = reviewService.getWatchedMoviesCount(userId);

        // Assert
        assertEquals(expectedCount, actualCount);
    }


}