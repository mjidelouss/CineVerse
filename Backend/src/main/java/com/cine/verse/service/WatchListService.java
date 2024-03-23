package com.cine.verse.service;

import com.cine.verse.domain.WatchList;
import java.util.List;

public interface WatchListService {
    List<WatchList> getWatchListByUser(Long userId);
    WatchList getWatchListByMovieIdAndUserId(Long movieId, Long userId);
    void deleteWatchList(Long id);
}
