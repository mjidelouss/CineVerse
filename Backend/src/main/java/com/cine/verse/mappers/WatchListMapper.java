package com.cine.verse.mappers;

import com.cine.verse.Dto.response.UserWatchListResponse;
import com.cine.verse.domain.WatchList;

public class WatchListMapper {
    public static UserWatchListResponse convertWatchListToUserWatchList(WatchList watchList) {
        return UserWatchListResponse.builder()
                .movieId(watchList.getMovie().getId())
                .movieImage(watchList.getMovie().getImage())
                .movieTitle(watchList.getMovie().getTitle())
                .movieYear(watchList.getMovie().getYear())
                .build();
    }
}
