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
    public List<WatchList> getWatchLists() {
        return watchListRepository.findAll();
    }

    @Override
    public WatchList getWatchListById(Long id) {
        return watchListRepository.findById(id).orElse(null);
    }

    @Override
    public WatchList addWatchList(WatchList watchList) {
        return watchListRepository.save(watchList);
    }

    @Override
    public WatchList updateWatchList(WatchList watchList, Long id) {
        return null;
    }

    @Override
    public void deleteWatchList(Long id) {
        watchListRepository.deleteById(id);
    }
}
