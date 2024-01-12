package com.cine.verse.Mapper;

import com.cine.verse.Dto.request.MovieListRequest;
import com.cine.verse.Dto.response.MovieListResponse;
import com.cine.verse.domain.MovieList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieListMapper {
    MovieList movieListRequestToMovieList(MovieListRequest movieListRequest);

    MovieListResponse movieListToMovieListResponse(MovieList movieList);
}
