package com.cine.verse.Mapper;

import com.cine.verse.domain.WatchList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WatchListMapper {
    WatchList watchListRequestToWatchList(WatchListRequest watchListRequest);

    WatchListResponse watchListToWatchListResponse(WatchList watchList);
}
