package com.cine.verse.service;

import com.cine.verse.domain.Like;

import java.util.List;

public interface LikeService {
    List<Like> getLikes();

    Like getLikeById(Long id);

    Like addLike(Like like);

    Like updateLike(Like like, Long id);

    void deleteLike(Long id);
}
