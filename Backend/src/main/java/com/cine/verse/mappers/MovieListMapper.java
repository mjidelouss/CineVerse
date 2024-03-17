package com.cine.verse.mappers;

import com.cine.verse.Dto.request.MovieListRequest;
import com.cine.verse.Dto.response.MovieListResponse;
import com.cine.verse.domain.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieListMapper {
    List movieListRequestToMovieList(MovieListRequest movieListRequest);

    MovieListResponse movieListToMovieListResponse(List movieList);
}
