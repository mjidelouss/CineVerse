package com.cine.verse.service.impl;

import com.cine.verse.domain.Like;
import com.cine.verse.repository.LikeRepository;
import com.cine.verse.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public List<Like> getLikes() {
        return likeRepository.findAll();
    }

    @Override
    public Like getLikeById(Long id) {
        return likeRepository.findById(id).orElse(null);
    }

    @Override
    public Like addLike(Like like) {
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
