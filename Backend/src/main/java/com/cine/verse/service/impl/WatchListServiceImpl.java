package com.cine.verse.service.impl;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.WatchList;
import com.cine.verse.repository.WatchListRepository;
import com.cine.verse.service.AppUserService;
import com.cine.verse.service.MovieService;
import com.cine.verse.service.WatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchListServiceImpl implements WatchListService {

    private final WatchListRepository watchListRepository;
    private final AppUserService appUserService;
    private final MovieService movieService;


    @Override
    public List<WatchList> getWatchLists() {
        return watchListRepository.findAll();
    }

    @Override
    public WatchList getWatchListById(Long id) {
        return watchListRepository.findById(id).orElse(null);
    }

    @Override
    public WatchList addWatchList(Long userId, Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        AppUser user = appUserService.getUserById(userId);
        WatchList watchList = WatchList.builder()
                .movie(movie)
                .appUser(user)
                .timestamp(LocalDate.now())
                .build();
        return watchListRepository.save(watchList);
    }

    @Override
    public List<WatchList> getWatchListByUser(Long userId) {
        return watchListRepository.findByAppUserId(userId);
    }

    @Override
    public WatchList getWatchListByMovieIdAndUserId(Long movieId, Long userId) {
        return watchListRepository.findByMovieIdAndAppUserId(movieId, userId);
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
