package com.cine.verse.service.impl;

import com.cine.verse.Dto.response.ReviewResponse;
import com.cine.verse.domain.AppUser;
import com.cine.verse.mappers.ReviewMapper;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;
import com.cine.verse.repository.AppUserRepository;
import com.cine.verse.repository.ReviewRepository;
import com.cine.verse.service.AppUserService;
import com.cine.verse.service.MovieService;
import com.cine.verse.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final AppUserRepository userRepository;
    private final MovieService movieService;


    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getRecentReviews(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<Object[]> getReviewdMovies(Long userId) {
        return reviewRepository.findMoviesAndRatingsByAppUserId(userId);
    }

    @Override
    public Review getReviewByMovieAndUser(Long movieId, Long userId) {
        return reviewRepository.findByMovieIdAppUserId(movieId, userId).orElse(null);
    }

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Review review, Long id) {
        return null;
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewResponse rateMovie(Long movieId, Long userId, Integer rate) {
        Review review = reviewRepository.findByMovieIdAppUserId(movieId, userId).orElse(null);

        if (review == null) {
            review = new Review();
            Movie movie = movieService.getMovieById(movieId);
            AppUser user1 = userRepository.findById(userId).orElse(null);
            review.setMovie(movie);
            review.setAppUser(user1);
        }
        review.setRating(rate);
        review.setTimestamp(LocalDate.now());
        review.setWatched(true);
        addReview(review);
        // Convert the review to a response and return
        ReviewResponse reviewResponse = reviewMapper.reviewToReviewResponse(review);
        return reviewResponse;
    }
}
