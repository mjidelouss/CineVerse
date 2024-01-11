package com.cine.verse.service;

import com.cine.verse.domain.WatchList;

import java.util.List;

public interface WatchListService {
    List<WatchList> getWatchLists();

    WatchList getWatchListById(Long id);

    WatchList addWatchList(WatchList watchList);

    WatchList updateWatchList(WatchList watchList, Long id);

    void deleteWatchList(Long id);
}
