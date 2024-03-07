package com.cine.verse.mappers;

import com.cine.verse.Dto.request.WatchListRequest;
import com.cine.verse.Dto.response.WatchListResponse;
import com.cine.verse.domain.WatchList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WatchListMapper {
    WatchList watchListRequestToWatchList(WatchListRequest watchListRequest);

    WatchListResponse watchListToWatchListResponse(WatchList watchList);
}
