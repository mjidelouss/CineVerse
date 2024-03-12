package com.cine.verse.service.impl;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Like;
import com.cine.verse.domain.Review;
import com.cine.verse.repository.LikeRepository;
import com.cine.verse.service.AppUserService;
import com.cine.verse.service.LikeService;
import com.cine.verse.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final ReviewService reviewService;
    private final AppUserService appUserService;

    @Override
    public List<Like> getLikes() {
        return likeRepository.findAll();
    }

    @Override
    public Like getLikeById(Long id) {
        return likeRepository.findById(id).orElse(null);
    }

    @Override
    public Like addLike(Long userId, Long reviewId) {
        Like like = new Like();
        Review review = reviewService.getReviewById(reviewId);
        AppUser user = appUserService.getUserById(userId);
        like.setReview(review);
        like.setAppUser(user);
        return likeRepository.save(like);
    }

    @Override
    public Like updateLike(Like like, Long id) {
        return null;
    }

    @Override
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }
}
