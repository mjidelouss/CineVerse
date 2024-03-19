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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Review> getReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public List<Review> getRecentReviews(Long movieId) {
        return reviewRepository.findByMovieIdAndContentIsNotNull(movieId);
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
    public long getTotalReviews() {
        return reviewRepository.countReviewsWithContentNotNull();
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
        if (rate == 0) {
            review.setWatched(false);
        } else {
            review.setWatched(true);
        }
        addReview(review);
        // Convert the review to a response and return
        ReviewResponse reviewResponse = reviewMapper.reviewToReviewResponse(review);
        return reviewResponse;
    }

    @Override
    public Boolean likeMovie(Long movieId, Long userId, Boolean like) {
        Review review = reviewRepository.findByMovieIdAppUserId(movieId, userId).orElse(null);
        if (review == null) {
            review = new Review();
            Movie movie = movieService.getMovieById(movieId);
            AppUser user1 = userRepository.findById(userId).orElse(null);
            review.setMovie(movie);
            review.setAppUser(user1);
        }
        review.setLiked(like);
        addReview(review);
        return like;
    }

    @Override
    public Boolean watchListMovie(Long movieId, Long userId, Boolean watchlist) {
        Review review = reviewRepository.findByMovieIdAppUserId(movieId, userId).orElse(null);
        if (review == null) {
            review = new Review();
            Movie movie = movieService.getMovieById(movieId);
            AppUser user1 = userRepository.findById(userId).orElse(null);
            review.setMovie(movie);
            review.setAppUser(user1);
        }
        review.setWatchlist(watchlist);
        addReview(review);
        return watchlist;
    }

    @Override
    public List<Review> getUserRecentReviews(Long userId) {
        return reviewRepository.findTop3ByAppUserIdAndContentIsNotNullOrderByTimestampDesc(userId);
    }

    @Override
    public List<Review> getUserReviews(Long userId) {
        return reviewRepository.findByAppUserIdAndContentIsNotNull(userId);
    }

    @Override
    public List<Movie> getMoviesLiked(Long userId) {
        return reviewRepository.findMoviesByAppUserIdAndLikedTrue(userId);
    }
}
