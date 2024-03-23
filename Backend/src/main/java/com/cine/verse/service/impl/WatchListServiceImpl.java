package com.cine.verse.service.impl;

import com.cine.verse.domain.WatchList;
import com.cine.verse.repository.WatchListRepository;
import com.cine.verse.service.WatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchListServiceImpl implements WatchListService {

    private final WatchListRepository watchListRepository;
    @Override
    public List<WatchList> getWatchListByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return watchListRepository.findByAppUserId(userId);
    }

    @Override
    public WatchList getWatchListByMovieIdAndUserId(Long movieId, Long userId) {
        if (movieId == null) {
            throw new IllegalArgumentException("movieId cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return watchListRepository.findByMovieIdAndAppUserId(movieId, userId);
    }

    @Override
    public void deleteWatchList(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        watchListRepository.deleteById(id);
    }
}
