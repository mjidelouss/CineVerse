package com.cine.verse.service;

import com.cine.verse.domain.Like;

import java.util.List;

public interface LikeService {
    List<Like> getLikes();

    Like getLikeById(Long id);

    Like addLike(Long userId, Long reviewId);

    Like updateLike(Like like, Long id);

    void deleteLike(Long id);
}
