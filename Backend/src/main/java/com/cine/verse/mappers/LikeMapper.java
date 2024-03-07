package com.cine.verse.mappers;

import com.cine.verse.Dto.request.LikeRequest;
import com.cine.verse.Dto.response.LikeResponse;
import com.cine.verse.domain.Like;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    Like likeRequestToLike(LikeRequest likeRequest);

    LikeResponse genreToLikeResponse(Like like);
}
