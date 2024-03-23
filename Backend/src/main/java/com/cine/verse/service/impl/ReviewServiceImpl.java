package com.cine.verse.service.impl;

import com.cine.verse.Dto.request.ReviewRequest;
import com.cine.verse.domain.AppUser;
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
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppUserRepository userRepository;
    private final MovieService movieService;
    private final AppUserService appUserService;


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
    public Review addReview(ReviewRequest reviewRequest) {
        Review review1;
        Review review = getReviewByMovieAndUser(reviewRequest.getMovieId(), reviewRequest.getUserId());

        if (review == null) {
            // If the review doesn't exist, create a new one
            review = new Review();
            review.setAppUser(appUserService.getUserById(reviewRequest.getUserId()));
            review.setMovie(movieService.getMovieById(reviewRequest.getMovieId()));
            review.setContent(reviewRequest.getContent());
            review.setTimestamp(LocalDate.now());
            review1 = reviewRepository.save(review);
        } else {
            if (review.getContent() == null) {
                // If the existing review has no content, update it
                review.setContent(reviewRequest.getContent());
                review.setTimestamp(LocalDate.now());
                review1 = reviewRepository.save(review);
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
                review1 = reviewRepository.save(review2);
            }
        }
        return review1;
    }

    @Override
    public Review updateReview(Review review) {
        return reviewRepository.save(review);
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
    public List<Review> getPopularReviews() {
        return reviewRepository.findDistinctTop6ByContentIsNotNullAndRatingIsNotNull();
    }
    @Override
    public Boolean rateMovie(Long movieId, Long userId, Integer rate) {
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
        reviewRepository.save(review);
        return true;
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
        reviewRepository.save(review);
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
        reviewRepository.save(review);
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
    public Long getWatchedMoviesCount(Long userId) {
        return reviewRepository.countDistinctMoviesWatchedByUserId(userId);
    }

    @Override
    public Long getLikedMoviesCount(Long userId) {
        return reviewRepository.countDistinctMoviesLikedByUserId(userId);
    }

    @Override
    public Long getCountOfUsersLikedMovie(Long movieId) {
        return reviewRepository.countDistinctUsersLikedByMovieId(movieId);
    }

    @Override
    public Long getCountOfUsersWatchedMovie(Long movieId) {
        return reviewRepository.countDistinctUsersWatchedByMovieId(movieId);
    }

    @Override
    public List<Movie> getMoviesLiked(Long userId) {
        return reviewRepository.findMoviesByAppUserIdAndLikedTrue(userId);
    }
}
